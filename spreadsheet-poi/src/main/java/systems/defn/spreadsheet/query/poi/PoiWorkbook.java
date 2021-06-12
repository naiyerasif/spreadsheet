package systems.defn.spreadsheet.query.poi;

import org.apache.poi.ss.usermodel.Workbook;
import systems.defn.spreadsheet.api.Sheet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class PoiWorkbook implements systems.defn.spreadsheet.api.Workbook {

  private final Workbook workbook;

  PoiWorkbook(Workbook workbook) {
    this.workbook = workbook;
  }

  public List<Sheet> getSheets() {
    List<Sheet> sheets = new ArrayList<>();
    workbook.forEach(sheet -> sheets.add(new PoiSheet(this, sheet)));
    return Collections.unmodifiableList(sheets);
  }

  Workbook getWorkbook() {
    return workbook;
  }
}
