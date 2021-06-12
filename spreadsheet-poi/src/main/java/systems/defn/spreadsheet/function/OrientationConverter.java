package systems.defn.spreadsheet.function;

import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.xssf.usermodel.XSSFPrintSetup;
import systems.defn.spreadsheet.api.Keywords;

public final class OrientationConverter {

  private OrientationConverter() {}

  public static Keywords.Orientation getOrientation(PrintSetup printSetup) {
    Keywords.Orientation orientation = null;

    if (printSetup instanceof HSSFPrintSetup hssfPrintSetup) {
      orientation = hssfPrintSetup.getLandscape() ? Keywords.Orientation.LANDSCAPE : Keywords.Orientation.PORTRAIT;
    }

    if (printSetup instanceof XSSFPrintSetup xssfPrintSetup) {
      orientation = switch (xssfPrintSetup.getOrientation()) {
        case PORTRAIT -> Keywords.Orientation.PORTRAIT;
        case LANDSCAPE -> Keywords.Orientation.LANDSCAPE;
        default -> null;
      };
    }

    return orientation;
  }
}
