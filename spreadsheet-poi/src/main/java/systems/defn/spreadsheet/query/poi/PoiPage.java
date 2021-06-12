package systems.defn.spreadsheet.query.poi;

import org.apache.poi.ss.usermodel.PrintSetup;
import systems.defn.spreadsheet.api.Keywords;
import systems.defn.spreadsheet.api.Page;
import systems.defn.spreadsheet.function.OrientationConverter;
import systems.defn.spreadsheet.function.PaperSizeConverter;

class PoiPage implements Page {

  private final PrintSetup printSetup;

  PoiPage(PoiSheet sheet) {
    this.printSetup = sheet.getSheet().getPrintSetup();
  }

  @Override
  public Keywords.Orientation getOrientation() {
    return OrientationConverter.getOrientation(printSetup);
  }

  @Override
  public Keywords.Paper getPaper() {
    return PaperSizeConverter.getPaper(printSetup);
  }

  final PrintSetup getPrintSetup() {
    return printSetup;
  }
}
