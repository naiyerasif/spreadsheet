package systems.defn.spreadsheet.query.api;

import systems.defn.spreadsheet.api.Cell;
import systems.defn.spreadsheet.api.Row;

import java.util.function.Consumer;
import java.util.function.Predicate;

public interface RowCriterion extends Predicate<Cell> {

  RowCriterion cell(int column);

  RowCriterion cell(int from, int to);

  RowCriterion cell(String column);

  RowCriterion cell(String from, String to);

  RowCriterion cell(Consumer<CellCriterion> cellCriterion);

  RowCriterion cell(int column, Consumer<CellCriterion> cellCriterion);

  RowCriterion cell(int from, int to, Consumer<CellCriterion> cellCriterion);

  RowCriterion cell(String column, Consumer<CellCriterion> cellCriterion);

  RowCriterion cell(String from, String to, Consumer<CellCriterion> cellCriterion);

  RowCriterion or(Consumer<RowCriterion> rowCriterion);

  RowCriterion having(Predicate<Row> rowPredicate);
}
