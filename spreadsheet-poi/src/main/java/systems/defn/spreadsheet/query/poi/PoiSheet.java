package systems.defn.spreadsheet.query.poi;

import org.apache.poi.ss.usermodel.SheetVisibility;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import systems.defn.spreadsheet.api.Page;
import systems.defn.spreadsheet.api.Row;
import systems.defn.spreadsheet.api.Sheet;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

class PoiSheet implements Sheet {

  private final PoiWorkbook workbook;
  private final org.apache.poi.ss.usermodel.Sheet poiSheet;

  private Map<Integer, PoiRow> rows;

  public PoiSheet(PoiWorkbook workbook, org.apache.poi.ss.usermodel.Sheet poiSheet) {
    this.workbook = workbook;
    this.poiSheet = poiSheet;
  }

  @Override
  public String getName() {
    return poiSheet.getSheetName();
  }

  @Override
  public PoiWorkbook getWorkbook() {
    return workbook;
  }

  protected org.apache.poi.ss.usermodel.Sheet getSheet() {
    return poiSheet;
  }

  @Override
  public List<Row> getRows() {
    if (Objects.isNull(rows)) {
      rows = new LinkedHashMap<>(poiSheet.getLastRowNum());
      poiSheet.forEach(row -> {
        final var oneBasedIndex = row.getRowNum() + 1;
        rows.put(oneBasedIndex, createRowWrapper(oneBasedIndex));
      });
    }

    return List.copyOf(rows.values());
  }

  PoiRow createRowWrapper(int oneBasedRowNumber) {
    return new PoiRow(this, poiSheet.getRow(oneBasedRowNumber - 1));
  }

  PoiRow getRowByNumber(int oneBasedRowNumber) {
    if (Objects.isNull(rows)) {
      this.getRows();
    }
    return rows.get(oneBasedRowNumber);
  }

  @Override
  public Page getPage() {
    return new PoiPage(this);
  }

  @Override
  public Sheet getNext() {
    final Workbook workbook = getWorkbook().getWorkbook();
    final int current = workbook.getSheetIndex(poiSheet.getSheetName());

    if (current == workbook.getNumberOfSheets() - 1) {
      return null;
    }

    final org.apache.poi.ss.usermodel.Sheet next = workbook.getSheetAt(current + 1);
    for (Sheet sheet : getWorkbook().getSheets()) {
      if (sheet.getName().equals(next.getSheetName())) {
        return sheet;
      }
    }

    return new PoiSheet(getWorkbook(), next);
  }

  @Override
  public Sheet getPrevious() {
    final Workbook workbook = getWorkbook().getWorkbook();
    final int current = workbook.getSheetIndex(poiSheet.getSheetName());

    if (current == 0) {
      return null;
    }

    final org.apache.poi.ss.usermodel.Sheet prev = workbook.getSheetAt(current - 1);
    for (Sheet sheet : getWorkbook().getSheets()) {
      if (sheet.getName().equals(prev.getSheetName())) {
        return sheet;
      }
    }

    return new PoiSheet(getWorkbook(), prev);
  }

  @Override
  public boolean isLocked() {
    return poiSheet instanceof XSSFSheet ps && ps.isSheetLocked();
  }

  @Override
  public boolean isHidden() {
    Workbook workbook = poiSheet.getWorkbook();
    return workbook.getSheetVisibility(workbook.getSheetIndex(poiSheet)) == SheetVisibility.HIDDEN;
  }

  @Override
  public boolean isVisible() {
    Workbook workbook = poiSheet.getWorkbook();
    return workbook.getSheetVisibility(workbook.getSheetIndex(poiSheet)) == SheetVisibility.VISIBLE;
  }

  @Override
  public boolean isVeryHidden() {
    Workbook workbook = poiSheet.getWorkbook();
    return workbook.getSheetVisibility(workbook.getSheetIndex(poiSheet)) == SheetVisibility.VERY_HIDDEN;
  }
}
