package systems.defn.spreadsheet.builder.api;

import java.util.function.Consumer;

public interface SpreadsheetBuilder {

  void build(Consumer<WorkbookDefinition> workbookDefinition);
}
