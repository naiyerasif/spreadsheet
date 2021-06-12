package systems.defn.spreadsheet.query.api;

import systems.defn.spreadsheet.api.BorderPositionProvider;
import systems.defn.spreadsheet.api.CellStyle;
import systems.defn.spreadsheet.api.Color;
import systems.defn.spreadsheet.api.ColorProvider;
import systems.defn.spreadsheet.api.ForegroundFill;
import systems.defn.spreadsheet.api.ForegroundFillProvider;
import systems.defn.spreadsheet.api.Keywords;

import java.util.function.Consumer;
import java.util.function.Predicate;

public interface CellStyleCriterion extends ForegroundFillProvider, BorderPositionProvider, ColorProvider {

  CellStyleCriterion background(String hexColor);

  CellStyleCriterion background(Color color);

  CellStyleCriterion background(Predicate<Color> predicate);

  CellStyleCriterion foreground(String hexColor);

  CellStyleCriterion foreground(Color color);

  CellStyleCriterion foreground(Predicate<Color> predicate);

  CellStyleCriterion fill(ForegroundFill fill);

  CellStyleCriterion fill(Predicate<ForegroundFill> predicate);

  CellStyleCriterion indent(int indent);

  CellStyleCriterion indent(Predicate<Integer> predicate);

  CellStyleCriterion rotation(int rotation);

  CellStyleCriterion rotation(Predicate<Integer> predicate);

  CellStyleCriterion format(String format);

  CellStyleCriterion format(Predicate<String> format);

  CellStyleCriterion font(Consumer<FontCriterion> fontCriterion);

  CellStyleCriterion border(Consumer<BorderCriterion> borderConfiguration);

  CellStyleCriterion border(Keywords.BorderSide location, Consumer<BorderCriterion> borderConfiguration);

  CellStyleCriterion border(Keywords.BorderSide first, Keywords.BorderSide second,
      Consumer<BorderCriterion> borderConfiguration);

  CellStyleCriterion border(Keywords.BorderSide first, Keywords.BorderSide second, Keywords.BorderSide third,
      Consumer<BorderCriterion> borderConfiguration);

  CellStyleCriterion having(Predicate<CellStyle> cellStylePredicate);
}
