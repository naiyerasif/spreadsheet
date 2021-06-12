package systems.defn.spreadsheet.query.simple;

import systems.defn.spreadsheet.api.CellStyle;
import systems.defn.spreadsheet.api.Color;
import systems.defn.spreadsheet.api.Font;
import systems.defn.spreadsheet.api.FontStyle;
import systems.defn.spreadsheet.query.api.FontCriterion;

import java.util.EnumSet;
import java.util.function.Predicate;

final class SimpleFontCriterion implements FontCriterion {

  private final SimpleCellCriterion parent;

  SimpleFontCriterion(SimpleCellCriterion parent) {
    this.parent = parent;
  }

  @Override
  public SimpleFontCriterion color(String hexColor) {
    color(new Color(hexColor));
    return this;
  }

  @Override
  public SimpleFontCriterion color(final Color color) {
    parent.addCondition(o -> {
      CellStyle style = o.getStyle();
      if (style == null) {
        return false;
      }
      Font font = style.getFont();
      return font != null && color.equals(font.getColor());
    });
    return this;
  }

  @Override
  public SimpleFontCriterion color(final Predicate<Color> condition) {
    parent.addCondition(o -> {
      CellStyle style = o.getStyle();
      if (style == null) {
        return false;
      }
      Font font = style.getFont();
      return font != null && condition.test(font.getColor());
    });
    return this;
  }

  @Override
  public SimpleFontCriterion size(final int size) {
    parent.addCondition(o -> {
      CellStyle style = o.getStyle();
      if (style == null) {
        return false;
      }
      Font font = style.getFont();
      return font != null && size == font.getSize();
    });
    return this;
  }

  @Override
  public SimpleFontCriterion size(final Predicate<Integer> predicate) {
    parent.addCondition(o -> {
      CellStyle style = o.getStyle();
      if (style == null) {
        return false;
      }
      Font font = style.getFont();
      return font != null && predicate.test(font.getSize());
    });
    return this;
  }

  @Override
  public SimpleFontCriterion name(final String name) {
    parent.addCondition(o -> {
      CellStyle style = o.getStyle();
      if (style == null) {
        return false;
      }
      Font font = style.getFont();
      return font != null && name.equals(font.getName());
    });
    return this;
  }

  @Override
  public SimpleFontCriterion name(final Predicate<String> predicate) {
    parent.addCondition(o -> {
      CellStyle style = o.getStyle();
      if (style == null) {
        return false;
      }
      Font font = style.getFont();
      return font != null && predicate.test(font.getName());
    });
    return this;
  }

  @Override
  public SimpleFontCriterion style(final FontStyle first, final FontStyle... other) {
    parent.addCondition(o -> {
      CellStyle style = o.getStyle();
      if (style == null) {
        return false;
      }
      Font font = style.getFont();
      if (font == null) {
        return false;
      }

      EnumSet<FontStyle> wanted = EnumSet.of(first, other);
      EnumSet<FontStyle> actual = font.getStyles();

      for (FontStyle fs : wanted) {
        if (!actual.contains(fs)) {
          return false;
        }
      }

      return true;
    });
    return this;
  }

  @Override
  public SimpleFontCriterion style(final Predicate<EnumSet<FontStyle>> predicate) {
    parent.addCondition(o -> {
      CellStyle style = o.getStyle();
      if (style == null) {
        return false;
      }
      Font font = style.getFont();
      return font != null && predicate.test(font.getStyles());
    });
    return this;
  }

  @Override
  public FontCriterion having(final Predicate<Font> fontPredicate) {
    parent.addCondition(o -> {
      CellStyle style = o.getStyle();
      if (style == null) {
        return false;
      }
      Font font = style.getFont();
      return font != null && fontPredicate.test(font);
    });
    return this;
  }
}
