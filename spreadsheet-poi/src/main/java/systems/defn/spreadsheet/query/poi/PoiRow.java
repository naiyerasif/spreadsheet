package systems.defn.spreadsheet.query.poi;

import systems.defn.spreadsheet.api.Cell;
import systems.defn.spreadsheet.api.Row;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

class PoiRow implements Row {

  private final PoiSheet sheet;
  private final org.apache.poi.ss.usermodel.Row poiRow;

  private Map<Integer, PoiCell> cells;

  PoiRow(PoiSheet sheet, org.apache.poi.ss.usermodel.Row poiRow) {
    this.sheet = sheet;
    this.poiRow = poiRow;
  }

  @Override
  public PoiSheet getSheet() {
    return sheet;
  }

  protected org.apache.poi.ss.usermodel.Row getRow() {
    return poiRow;
  }

  @Override
  public List<PoiCell> getCells() {
    if (Objects.isNull(cells)) {
      cells = new LinkedHashMap<>();
      poiRow.forEach(cell -> cells.put(cell.getColumnIndex() + 1, new PoiCell(this, cell)));
    }

    return List.copyOf(cells.values());
  }

  @Override
  public int getNumber() {
    return poiRow.getRowNum() + 1;
  }

  @Override
  public PoiRow getAbove() {
    return getAbove(1);
  }

  @Override
  public PoiRow getAbove(int howMany) {
    return aboveOrBelow(-howMany);
  }

  @Override
  public PoiRow getBelow() {
    return getBelow(1);
  }

  @Override
  public PoiRow getBelow(int howMany) {
    return aboveOrBelow(howMany);
  }

  Cell getCellByNumber(int oneBasedCellNumber) {
    if (Objects.isNull(cells)) {
      this.getCells();
    }
    return cells.get(oneBasedCellNumber);
  }

  private PoiRow aboveOrBelow(int howMany) {
    if (poiRow.getRowNum() + howMany < 0 || poiRow.getRowNum() + howMany > poiRow.getSheet().getLastRowNum()) {
      return null;
    }

    final PoiRow existing = sheet.getRowByNumber(getNumber() + howMany);
    return Objects.nonNull(existing) ? existing : null;
  }
}
