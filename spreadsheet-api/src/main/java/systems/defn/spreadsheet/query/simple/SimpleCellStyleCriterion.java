package systems.defn.spreadsheet.query.simple;

import systems.defn.spreadsheet.api.CellStyle;
import systems.defn.spreadsheet.api.Color;
import systems.defn.spreadsheet.api.ForegroundFill;
import systems.defn.spreadsheet.api.Keywords;
import systems.defn.spreadsheet.query.api.BorderCriterion;
import systems.defn.spreadsheet.query.api.CellStyleCriterion;
import systems.defn.spreadsheet.query.api.FontCriterion;

import java.util.function.Consumer;
import java.util.function.Predicate;

final class SimpleCellStyleCriterion implements CellStyleCriterion {

  private final SimpleCellCriterion parent;

  SimpleCellStyleCriterion(SimpleCellCriterion parent) {
    this.parent = parent;
  }

  @Override
  public SimpleCellStyleCriterion background(final String hexColor) {
    parent.addCondition(o -> o.getStyle() != null && new Color(hexColor).equals(o.getStyle().getBackground()));
    return this;
  }

  @Override
  public SimpleCellStyleCriterion background(final Color color) {
    parent.addCondition(o -> o.getStyle() != null && color.equals(o.getStyle().getBackground()));
    return this;
  }

  @Override
  public SimpleCellStyleCriterion background(final Predicate<Color> predicate) {
    parent.addCondition(o -> o.getStyle() != null && predicate.test(o.getStyle().getBackground()));
    return this;
  }

  @Override
  public SimpleCellStyleCriterion foreground(final String hexColor) {
    parent.addCondition(o -> o.getStyle() != null && new Color(hexColor).equals(o.getStyle().getForeground()));
    return this;
  }

  @Override
  public SimpleCellStyleCriterion foreground(final Color color) {
    parent.addCondition(o -> o.getStyle() != null && color.equals(o.getStyle().getForeground()));
    return this;
  }

  @Override
  public SimpleCellStyleCriterion foreground(final Predicate<Color> predicate) {
    parent.addCondition(o -> o.getStyle() != null && predicate.test(o.getStyle().getForeground()));
    return this;
  }

  @Override
  public SimpleCellStyleCriterion fill(final ForegroundFill fill) {
    parent.addCondition(o -> o.getStyle() != null && fill.equals(o.getStyle().getFill()));
    return this;
  }

  @Override
  public SimpleCellStyleCriterion fill(final Predicate<ForegroundFill> predicate) {
    parent.addCondition(o -> o.getStyle() != null && predicate.test(o.getStyle().getFill()));
    return this;
  }

  @Override
  public SimpleCellStyleCriterion indent(final int indent) {
    parent.addCondition(o -> o.getStyle() != null && indent == o.getStyle().getIndent());
    return this;
  }

  @Override
  public SimpleCellStyleCriterion indent(final Predicate<Integer> predicate) {
    parent.addCondition(o -> o.getStyle() != null && predicate.test(o.getStyle().getIndent()));
    return this;
  }

  @Override
  public SimpleCellStyleCriterion rotation(final int rotation) {
    parent.addCondition(o -> o.getStyle() != null && rotation == o.getStyle().getRotation());
    return this;
  }

  @Override
  public SimpleCellStyleCriterion rotation(final Predicate<Integer> predicate) {
    parent.addCondition(o -> o.getStyle() != null && predicate.test(o.getStyle().getRotation()));
    return this;
  }

  @Override
  public SimpleCellStyleCriterion format(final String format) {
    parent.addCondition(o -> o.getStyle() != null && format.equals(o.getStyle().getFormat()));
    return this;
  }

  @Override
  public SimpleCellStyleCriterion format(final Predicate<String> format) {
    parent.addCondition(o -> o.getStyle() != null && format.test(o.getStyle().getFormat()));
    return this;
  }

  @Override
  public SimpleCellStyleCriterion font(Consumer<FontCriterion> fontCriterion) {
    SimpleFontCriterion simpleFontCriterion = new SimpleFontCriterion(parent);
    fontCriterion.accept(simpleFontCriterion);
    return this;
  }

  @Override
  public SimpleCellStyleCriterion border(Consumer<BorderCriterion> borderConfiguration) {
    border(Keywords.BorderSide.BORDER_SIDES, borderConfiguration);
    return this;
  }

  @Override
  public SimpleCellStyleCriterion border(Keywords.BorderSide location, Consumer<BorderCriterion> borderConfiguration) {
    border(new Keywords.BorderSide[]{ location }, borderConfiguration);
    return this;
  }

  @Override
  public SimpleCellStyleCriterion border(Keywords.BorderSide first, Keywords.BorderSide second,
      Consumer<BorderCriterion> borderConfiguration) {
    border(new Keywords.BorderSide[]{ first, second }, borderConfiguration);
    return this;
  }

  @Override
  public SimpleCellStyleCriterion border(Keywords.BorderSide first, Keywords.BorderSide second,
      Keywords.BorderSide third, Consumer<BorderCriterion> borderConfiguration) {
    border(new Keywords.BorderSide[]{ first, second, third }, borderConfiguration);
    return this;
  }

  @Override
  public CellStyleCriterion having(final Predicate<CellStyle> cellStylePredicate) {
    parent.addCondition(o -> o.getStyle() != null && cellStylePredicate.test(o.getStyle()));
    return this;
  }

  private void border(Keywords.BorderSide[] sides, Consumer<BorderCriterion> borderConfiguration) {
    for (Keywords.BorderSide side : sides) {
      SimpleBorderCriterion criterion = new SimpleBorderCriterion(parent, side);
      borderConfiguration.accept(criterion);
    }
  }
}
