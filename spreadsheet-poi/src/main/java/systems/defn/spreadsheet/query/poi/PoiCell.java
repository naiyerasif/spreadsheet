package systems.defn.spreadsheet.query.poi;

import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFCell;
import systems.defn.spreadsheet.api.Cell;
import systems.defn.spreadsheet.api.CellStyle;
import systems.defn.spreadsheet.api.Comment;
import systems.defn.spreadsheet.base.DefaultCommentDefinition;
import systems.defn.spreadsheet.function.ColumnResolver;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;

class PoiCell implements Cell {

  private final PoiRow row;
  private final org.apache.poi.ss.usermodel.Cell poiCell;

  PoiCell(PoiRow row, org.apache.poi.ss.usermodel.Cell poiCell) {
    this.row = Objects.requireNonNull(row, "Row");
    this.poiCell = Objects.requireNonNull(poiCell, "Cell");
  }

  @Override
  public int getColumn() {
    return poiCell.getColumnIndex() + 1;
  }

  @Override
  public String getColumnAsString() {
    return ColumnResolver.toColumn(getColumn());
  }

  @Override
  public <T> T read(Class<T> type) {
    if (CharSequence.class.isAssignableFrom(type)) {
      return type.cast(poiCell.getStringCellValue());
    }

    if (Date.class.isAssignableFrom(type)) {
      return type.cast(poiCell.getDateCellValue());
    }

    if (LocalDateTime.class.isAssignableFrom(type)) {
      return type.cast(poiCell.getLocalDateTimeCellValue());
    }

    if (LocalDate.class.isAssignableFrom(type)) {
      final LocalDateTime localDateTime = poiCell.getLocalDateTimeCellValue();
      return Objects.nonNull(localDateTime) ? type.cast(localDateTime.toLocalDate()) : null;
    }

    if (LocalTime.class.isAssignableFrom(type)) {
      final LocalDateTime localDateTime = poiCell.getLocalDateTimeCellValue();
      return Objects.nonNull(localDateTime) ? type.cast(localDateTime.toLocalTime()) : null;
    }

    if (Boolean.class.isAssignableFrom(type)) {
      return type.cast(poiCell.getBooleanCellValue());
    }

    if (Number.class.isAssignableFrom(type)) {
      final Double value = poiCell.getNumericCellValue();
      return type.cast(value);
    }

    final String raw = poiCell instanceof XSSFCell xssfCell ? xssfCell.getRawValue() : poiCell.toString();
    throw new IllegalArgumentException("Cannot read value " + raw + " of cell as " + type);
  }

  @Override
  public Object getValue() {
    return switch (poiCell.getCellType()) {
      case BLANK -> "";
      case BOOLEAN -> poiCell.getBooleanCellValue();
      case ERROR -> poiCell.getErrorCellValue();
      case FORMULA -> poiCell.getCellFormula();
      case NUMERIC -> poiCell.getNumericCellValue();
      case STRING -> poiCell.getStringCellValue();
      default -> poiCell instanceof XSSFCell xssfCell ? xssfCell.getRawValue() : poiCell.toString();
    };
  }

  @Override
  public Comment getComment() {
    final org.apache.poi.ss.usermodel.Comment cellComment = poiCell.getCellComment();
    if (Objects.isNull(cellComment)) {
      return new DefaultCommentDefinition();
    }

    final var commentDefinition = new DefaultCommentDefinition();
    commentDefinition.author(cellComment.getAuthor());
    commentDefinition.text(cellComment.getString().getString());
    return commentDefinition;
  }

  @Override
  public CellStyle getStyle() {
    final org.apache.poi.ss.usermodel.CellStyle cellStyle = poiCell.getCellStyle();
    return Objects.nonNull(cellStyle) ? new PoiCellStyle(poiCell, cellStyle) : null;
  }

  // TODO: test this against formula references
  @Override
  public String getName() {
    final Workbook workbook = poiCell.getSheet().getWorkbook();
    final var cellReference = new CellReference(poiCell);
    final var reference = poiCell instanceof XSSFCell xssfCell ? xssfCell.getReference() :
        cellReference.formatAsString();
    final var formatAsFormula = "'" + poiCell.getSheet().getSheetName().replaceAll("'", "\\'") + "'!" + reference;

    final List<String> possibleReferences = List.of(cellReference.formatAsString(), formatAsFormula);
    for (Name name : workbook.getAllNames()) {
      if (name.getSheetIndex() == -1 || name.getSheetIndex() == workbook.getSheetIndex(poiCell.getSheet())) {
        for (String possibleReference : possibleReferences) {
          if (normalizeFormulaReference(name.getRefersToFormula())
              .equalsIgnoreCase(normalizeFormulaReference(possibleReference))) {
            return name.getNameName();
          }
        }
      }
    }

    return null;
  }

  private static String normalizeFormulaReference(String ref) {
    return ref.replace("$", "").replace("'", "");
  }

  @Override
  public int getColSpan() {
    if (row.getSheet().getSheet().getNumMergedRegions() == 0) {
      return 1;
    }

    for (CellRangeAddress candidate : row.getSheet().getSheet().getMergedRegions()) {
      if (candidate.isInRange(getCell().getRowIndex(), getCell().getColumnIndex())) {
        return candidate.getLastColumn() - candidate.getFirstColumn() + 1;
      }
    }

    return 1;
  }

  @Override
  public int getRowSpan() {
    if (row.getSheet().getSheet().getNumMergedRegions() == 0) {
      return 1;
    }

    for (CellRangeAddress candidate : row.getSheet().getSheet().getMergedRegions()) {
      if (candidate.isInRange(getCell().getRowIndex(), getCell().getColumnIndex())) {
        return candidate.getLastRow() - candidate.getFirstRow() + 1;
      }
    }

    return 1;
  }

  protected org.apache.poi.ss.usermodel.Cell getCell() {
    return poiCell;
  }

  @Override
  public PoiRow getRow() {
    return row;
  }

  @Override
  public Cell getAbove() {
    final PoiRow row = this.row.getAbove();
    if (Objects.isNull(row)) {
      return null;
    }

    final Cell existing = row.getCellByNumber(getColumn());
    if (Objects.nonNull(existing)) {
      return existing;
    }

    return createCellIfExists(getCell(row.getRow(), getColumn() - 1));
  }

  @Override
  public Cell getBelow() {
    final PoiRow row = this.row.getBelow(getRowSpan());
    if (Objects.isNull(row)) {
      return null;
    }

    final Cell existing = row.getCellByNumber(getColumn());
    if (Objects.nonNull(existing)) {
      return existing;
    }

    return createCellIfExists(getCell(row.getRow(), getColumn() - 1));
  }

  @Override
  public Cell getLeft() {
    if (getColumn() == 1) {
      return null;
    }

    final Cell existing = row.getCellByNumber(getColumn() - 1);
    if (Objects.nonNull(existing)) {
      return existing;
    }

    return createCellIfExists(getCell(row.getRow(), getColumn() - 2));
  }

  @Override
  public Cell getRight() {
    if (getColumn() + getColSpan() > row.getRow().getLastCellNum()) {
      return null;
    }

    final Cell existing = row.getCellByNumber(getColumn() + getColSpan());
    if (Objects.nonNull(existing)) {
      return existing;
    }

    return createCellIfExists(getCell(row.getRow(), getColumn() + getColSpan() - 1));
  }

  @Override
  public Cell getAboveLeft() {
    final PoiRow row = this.row.getAbove();
    if (Objects.isNull(row)) {
      return null;
    }

    if (getColumn() == 1) {
      return null;
    }

    final Cell existing = row.getCellByNumber(getColumn() - 1);
    if (Objects.nonNull(existing)) {
      return existing;
    }

    return createCellIfExists(getCell(row.getRow(), getColumn() - 2));
  }

  @Override
  public Cell getAboveRight() {
    final PoiRow row = this.row.getAbove();
    if (Objects.isNull(row)) {
      return null;
    }

    if (getColumn() == row.getRow().getLastCellNum()) {
      return null;
    }

    final Cell existing = row.getCellByNumber(getColumn() + 1);
    if (Objects.nonNull(existing)) {
      return existing;
    }

    return createCellIfExists(getCell(row.getRow(), getColumn()));
  }

  @Override
  public Cell getBelowLeft() {
    final PoiRow row = this.row.getBelow();
    if (Objects.isNull(row)) {
      return null;
    }

    if (getColumn() == 1) {
      return null;
    }

    final Cell existing = row.getCellByNumber(getColumn() - 1);
    if (Objects.nonNull(existing)) {
      return existing;
    }

    return createCellIfExists(getCell(row.getRow(), getColumn() - 2));
  }

  @Override
  public Cell getBelowRight() {
    final PoiRow row = this.row.getBelow();
    if (Objects.isNull(row)) {
      return null;
    }

    if (getColumn() == row.getRow().getLastCellNum()) {
      return null;
    }

    final Cell existing = row.getCellByNumber(getColumn() + 1);
    if (Objects.nonNull(existing)) {
      return existing;
    }

    return createCellIfExists(getCell(row.getRow(), getColumn()));
  }

  private PoiCell createCellIfExists(org.apache.poi.ss.usermodel.Cell cell) {
    if (Objects.nonNull(cell)) {
      final PoiRow number = row.getSheet().getRowByNumber(cell.getRowIndex() + 1);
      return new PoiCell(Objects.nonNull(number) ? number : row.getSheet()
          .createRowWrapper(cell.getRowIndex() + 1), cell);
    }

    return null;
  }

  @Override
  public String toString() {
    return "Cell[" + row.getSheet().getName()
        + "!" + getColumnAsString() + row.getNumber()
        + "]=" + getValue();
  }

  private static org.apache.poi.ss.usermodel.Cell getCell(Row row, int column) {
    final org.apache.poi.ss.usermodel.Cell cell = row.getCell(column);
    if (Objects.nonNull(cell)) {
      return cell;
    }

    if (row.getSheet().getNumMergedRegions() == 0) {
      return null;
    }

    CellRangeAddress address = null;

    for (CellRangeAddress candidate : row.getSheet().getMergedRegions()) {
      if (candidate.isInRange(row.getRowNum(), column)) {
        address = candidate;
        break;
      }
    }

    if (address == null) {
      return null;
    }

    return row.getSheet().getRow(address.getFirstRow()).getCell(address.getFirstColumn());
  }
}
