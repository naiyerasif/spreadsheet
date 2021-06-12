package systems.defn.spreadsheet.query.api;

import systems.defn.spreadsheet.api.Sheet;

import java.util.function.Consumer;
import java.util.function.Predicate;

public interface WorkbookCriterion extends Predicate<Sheet> {

  WorkbookCriterion sheet(String name);

  WorkbookCriterion sheet(String name, Consumer<SheetCriterion> sheetCriterion);

  WorkbookCriterion sheet(Consumer<SheetCriterion> sheetCriterion);

  WorkbookCriterion or(Consumer<WorkbookCriterion> workbookCriterion);
}
