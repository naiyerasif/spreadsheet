package systems.defn.spreadsheet.function;

import org.apache.poi.hssf.record.PaletteRecord;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.ExtendedColor;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.XSSFFont;
import systems.defn.spreadsheet.api.Color;

import java.util.Objects;

public final class ColorConverter {

  private static final PaletteRecord PALETTE_RECORD = new PaletteRecord();

  private ColorConverter() {}

  public static Color getColor(org.apache.poi.ss.usermodel.Color color) {
    Color convertedColor = null;

    if (color instanceof ExtendedColor extendedColor) {
      final byte[] rgb = extendedColor.getRGB();
      if (Objects.nonNull(rgb)) {
        convertedColor = new Color(rgb);
      }
    }

    if (color instanceof HSSFColor hssfColor) {
      final String hex = hssfColor.getHexString();
      if (Objects.nonNull(hex)) {
        convertedColor = new Color(hex);
      }
    }

    return convertedColor;
  }

  public static Color getColor(Font font) {
    Color convertedColor = null;

    if (font instanceof HSSFFont hssfFont) {
      convertedColor = getColor(hssfFont.getColor());
    }

    if (font instanceof XSSFFont xssfFont) {
      if (Objects.nonNull(xssfFont.getXSSFColor()) && Objects.nonNull(xssfFont.getXSSFColor().getRGB())) {
        convertedColor = new Color(xssfFont.getXSSFColor().getRGB());
      }
    }

    return convertedColor;
  }

  public static Color getColor(short index) {
    Color color = null;

    if (index == HSSFColor.HSSFColorPredefined.AUTOMATIC.getIndex()) {
      color = new Color(HSSFColor.HSSFColorPredefined.AUTOMATIC.getColor().getHexString());
    } else {
      final byte[] rgb = PALETTE_RECORD.getColor(index);
      if (Objects.nonNull(rgb)) {
        color = new Color(rgb);
      }
    }

    return color;
  }
}
