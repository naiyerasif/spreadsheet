package systems.defn.spreadsheet.query.api;

import systems.defn.spreadsheet.api.Border;
import systems.defn.spreadsheet.api.BorderStyle;
import systems.defn.spreadsheet.api.BorderStyleProvider;
import systems.defn.spreadsheet.api.Color;

import java.util.function.Predicate;

public interface BorderCriterion extends BorderStyleProvider {

  BorderCriterion style(BorderStyle style);

  BorderCriterion style(Predicate<BorderStyle> predicate);

  BorderCriterion color(String hexColor);

  BorderCriterion color(Color color);

  BorderCriterion color(Predicate<Color> predicate);

  BorderCriterion having(Predicate<Border> borderPredicate);
}
