package systems.defn.spreadsheet.query.poi;

import systems.defn.spreadsheet.api.Border;
import systems.defn.spreadsheet.api.BorderStyle;
import systems.defn.spreadsheet.api.Color;

import java.util.Objects;

class PoiBorder implements Border {

  private Color color;
  private org.apache.poi.ss.usermodel.BorderStyle borderStyle;

  PoiBorder(Color color, org.apache.poi.ss.usermodel.BorderStyle borderStyle) {
    this.color = color;
    this.borderStyle = borderStyle;
  }

  @Override
  public Color getColor() {
    return Objects.isNull(color) ? null : color;
  }

  @Override
  public BorderStyle getStyle() {
    return switch (borderStyle) {
      case NONE -> BorderStyle.NONE;
      case THIN -> BorderStyle.THIN;
      case MEDIUM -> BorderStyle.MEDIUM;
      case DASHED -> BorderStyle.DASHED;
      case DOTTED -> BorderStyle.DOTTED;
      case THICK -> BorderStyle.THICK;
      case DOUBLE -> BorderStyle.DOUBLE;
      case HAIR -> BorderStyle.HAIR;
      case MEDIUM_DASHED -> BorderStyle.MEDIUM_DASHED;
      case DASH_DOT -> BorderStyle.DASH_DOT;
      case MEDIUM_DASH_DOT -> BorderStyle.MEDIUM_DASH_DOT;
      case DASH_DOT_DOT -> BorderStyle.DASH_DOT_DOT;
      case MEDIUM_DASH_DOT_DOT -> BorderStyle.MEDIUM_DASH_DOT_DOT;
      case SLANTED_DASH_DOT -> BorderStyle.SLANTED_DASH_DOT;
    };
  }
}
