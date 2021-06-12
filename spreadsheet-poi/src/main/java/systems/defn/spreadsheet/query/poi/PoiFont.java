package systems.defn.spreadsheet.query.poi;

import org.apache.poi.ss.usermodel.FontUnderline;
import systems.defn.spreadsheet.api.Color;
import systems.defn.spreadsheet.api.Font;
import systems.defn.spreadsheet.api.FontStyle;
import systems.defn.spreadsheet.function.ColorConverter;

import java.util.EnumSet;

class PoiFont implements Font {

  private org.apache.poi.ss.usermodel.Font font;

  PoiFont(org.apache.poi.ss.usermodel.Font font) {
    this.font = font;
  }

  @Override
  public Color getColor() {
    return ColorConverter.getColor(font);
  }

  @Override
  public int getSize() {
    return font.getFontHeightInPoints();
  }

  @Override
  public String getName() {
    return font.getFontName();
  }

  @Override
  public EnumSet<FontStyle> getStyles() {
    EnumSet<FontStyle> styles = EnumSet.noneOf(FontStyle.class);

    if (font.getItalic()) {
      styles.add(FontStyle.ITALIC);
    }

    if (font.getBold()) {
      styles.add(FontStyle.BOLD);
    }

    if (font.getStrikeout()) {
      styles.add(FontStyle.STRIKEOUT);
    }

    if (font.getUnderline() != FontUnderline.NONE.getByteValue()) {
      styles.add(FontStyle.UNDERLINE);
    }

    return styles;
  }

  org.apache.poi.ss.usermodel.Font getFont() {
    return font;
  }

  void setFont(org.apache.poi.ss.usermodel.Font font) {
    this.font = font;
  }
}
