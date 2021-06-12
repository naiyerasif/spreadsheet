package systems.defn.spreadsheet.query.api;

import systems.defn.spreadsheet.api.Cell;
import systems.defn.spreadsheet.api.Row;
import systems.defn.spreadsheet.api.Sheet;

import java.util.Collection;

public interface SpreadsheetCriteriaResult extends Iterable<Cell> {

  Collection<Cell> getCells();

  Collection<Row> getRows();

  Collection<Sheet> getSheets();

  Cell getCell();

  Row getRow();

  Sheet getSheet();
}
