package systems.defn.spreadsheet.query.api;

import systems.defn.spreadsheet.api.Cell;

import java.io.FileNotFoundException;
import java.util.function.Consumer;

public interface SpreadsheetCriteria {

  SpreadsheetCriteriaResult all();

  SpreadsheetCriteriaResult query(Consumer<WorkbookCriterion> workbookCriterion) throws FileNotFoundException;

  Cell find(Consumer<WorkbookCriterion> workbookCriterion) throws FileNotFoundException;

  boolean exists(Consumer<WorkbookCriterion> workbookCriterion) throws FileNotFoundException;
}
