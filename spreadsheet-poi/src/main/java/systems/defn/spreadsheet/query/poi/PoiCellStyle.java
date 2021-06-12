package systems.defn.spreadsheet.query.poi;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.extensions.XSSFCellBorder;
import systems.defn.spreadsheet.api.Border;
import systems.defn.spreadsheet.api.CellStyle;
import systems.defn.spreadsheet.api.Color;
import systems.defn.spreadsheet.api.Font;
import systems.defn.spreadsheet.api.ForegroundFill;
import systems.defn.spreadsheet.api.Keywords;
import systems.defn.spreadsheet.function.ColorConverter;

import java.util.Objects;

class PoiCellStyle implements CellStyle {

  private final Cell cell;
  private final org.apache.poi.ss.usermodel.CellStyle style;

  PoiCellStyle(Cell cell, org.apache.poi.ss.usermodel.CellStyle style) {
    this.cell = cell;
    this.style = style;
  }

  @Override
  public Color getForeground() {
    return ColorConverter.getColor(style.getFillForegroundColorColor());
  }

  @Override
  public Color getBackground() {
    return ColorConverter.getColor(style.getFillBackgroundColorColor());
  }

  @Override
  public ForegroundFill getFill() {
    return switch (style.getFillPattern()) {
      case NO_FILL -> ForegroundFill.NO_FILL;
      case SOLID_FOREGROUND -> ForegroundFill.SOLID_FOREGROUND;
      case FINE_DOTS -> ForegroundFill.FINE_DOTS;
      case ALT_BARS -> ForegroundFill.ALT_BARS;
      case SPARSE_DOTS -> ForegroundFill.SPARSE_DOTS;
      case THICK_HORZ_BANDS -> ForegroundFill.THICK_HORZ_BANDS;
      case THICK_VERT_BANDS -> ForegroundFill.THICK_VERT_BANDS;
      case THICK_BACKWARD_DIAG -> ForegroundFill.THICK_BACKWARD_DIAG;
      case THICK_FORWARD_DIAG -> ForegroundFill.THICK_FORWARD_DIAG;
      case BIG_SPOTS -> ForegroundFill.BIG_SPOTS;
      case BRICKS -> ForegroundFill.BRICKS;
      case THIN_HORZ_BANDS -> ForegroundFill.THIN_HORZ_BANDS;
      case THIN_VERT_BANDS -> ForegroundFill.THIN_VERT_BANDS;
      case THIN_BACKWARD_DIAG -> ForegroundFill.THIN_BACKWARD_DIAG;
      case THIN_FORWARD_DIAG -> ForegroundFill.THIN_FORWARD_DIAG;
      case SQUARES -> ForegroundFill.SQUARES;
      case DIAMONDS -> ForegroundFill.DIAMONDS;
      case LESS_DOTS -> ForegroundFill.LESS_DOTS;
      case LEAST_DOTS -> ForegroundFill.LEAST_DOTS;
    };
  }

  @Override
  public int getIndent() {
    return style.getIndention();
  }

  @Override
  public int getRotation() {
    return style.getRotation();
  }

  @Override
  public String getFormat() {
    return style.getDataFormatString();
  }

  @Override
  public Font getFont() {
    Font font = null;
    final Workbook workbook = cell.getSheet().getWorkbook();

    if (style instanceof HSSFCellStyle hssfCellStyle && workbook instanceof HSSFWorkbook) {
      final HSSFFont hssfFont = hssfCellStyle.getFont(workbook);
      if (Objects.nonNull(hssfFont)) {
        font = new PoiFont(hssfFont);
      }
    }

    if (style instanceof XSSFCellStyle xssfCellStyle) {
      final XSSFFont xssfFont = xssfCellStyle.getFont();
      if (Objects.nonNull(xssfFont)) {
        font = new PoiFont(xssfFont);
      }
    }

    return font;
  }

  @Override
  public Border getBorder(Keywords.BorderSide borderSide) {
    if (style instanceof HSSFCellStyle) {
      if (Keywords.BorderSide.TOP.equals(borderSide)) {
        return new PoiBorder(ColorConverter.getColor(style.getTopBorderColor()), style.getBorderTop());
      } else if (Keywords.BorderSide.BOTTOM.equals(borderSide)) {
        return new PoiBorder(ColorConverter.getColor(style.getBottomBorderColor()), style.getBorderBottom());
      } else if (Keywords.BorderSide.LEFT.equals(borderSide)) {
        return new PoiBorder(ColorConverter.getColor(style.getLeftBorderColor()), style.getBorderLeft());
      } else if (Keywords.BorderSide.RIGHT.equals(borderSide)) {
        return new PoiBorder(ColorConverter.getColor(style.getRightBorderColor()), style.getBorderRight());
      }
    }

    if (style instanceof XSSFCellStyle xssfCellStyle) {
      if (Keywords.BorderSide.TOP.equals(borderSide)) {
        return new PoiBorder(ColorConverter.getColor(xssfCellStyle.getBorderColor(XSSFCellBorder.BorderSide.TOP)),
            xssfCellStyle.getBorderTop());
      } else if (Keywords.BorderSide.BOTTOM.equals(borderSide)) {
        return new PoiBorder(ColorConverter.getColor(xssfCellStyle.getBorderColor(XSSFCellBorder.BorderSide.BOTTOM)),
            xssfCellStyle.getBorderTop());
      } else if (Keywords.BorderSide.LEFT.equals(borderSide)) {
        return new PoiBorder(ColorConverter.getColor(xssfCellStyle.getBorderColor(XSSFCellBorder.BorderSide.LEFT)),
            xssfCellStyle.getBorderTop());
      } else if (Keywords.BorderSide.RIGHT.equals(borderSide)) {
        return new PoiBorder(ColorConverter.getColor(xssfCellStyle.getBorderColor(XSSFCellBorder.BorderSide.RIGHT)),
            xssfCellStyle.getBorderTop());
      }
    }

    return new PoiBorder( null, null);
  }
}
