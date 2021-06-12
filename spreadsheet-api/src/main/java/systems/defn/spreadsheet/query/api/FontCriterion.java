package systems.defn.spreadsheet.query.api;

import systems.defn.spreadsheet.api.Color;
import systems.defn.spreadsheet.api.ColorProvider;
import systems.defn.spreadsheet.api.Font;
import systems.defn.spreadsheet.api.FontStyle;
import systems.defn.spreadsheet.api.FontStylesProvider;

import java.util.EnumSet;
import java.util.function.Predicate;

public interface FontCriterion extends FontStylesProvider, ColorProvider {

  FontCriterion color(String hexColor);

  FontCriterion color(Color color);

  FontCriterion color(Predicate<Color> predicate);

  FontCriterion size(int size);

  FontCriterion size(Predicate<Integer> predicate);

  FontCriterion name(String name);

  FontCriterion name(Predicate<String> predicate);

  FontCriterion style(FontStyle first, FontStyle... other);

  FontCriterion style(Predicate<EnumSet<FontStyle>> predicate);

  FontCriterion having(Predicate<Font> fontPredicate);
}
