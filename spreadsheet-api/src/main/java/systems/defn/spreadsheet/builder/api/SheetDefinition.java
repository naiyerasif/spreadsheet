package systems.defn.spreadsheet.builder.api;

import systems.defn.spreadsheet.api.Keywords;
import systems.defn.spreadsheet.api.SheetStateProvider;
import systems.defn.spreadsheet.function.ColumnResolver;

import java.util.function.Consumer;

public interface SheetDefinition extends SheetStateProvider {

  default SheetDefinition row() {
    return row(r -> {});
  }

  SheetDefinition row(Consumer<RowDefinition> rowDefinition);

  SheetDefinition row(int row, Consumer<RowDefinition> rowDefinition);

  SheetDefinition freeze(int column, int row);

  default SheetDefinition freeze(String column, int row) {
    return freeze(ColumnResolver.parseColumn(column), row);
  }

  SheetDefinition group(Consumer<SheetDefinition> insideGroupDefinition);

  SheetDefinition collapse(Consumer<SheetDefinition> insideGroupDefinition);

  SheetDefinition state(Keywords.SheetState state);

  SheetDefinition password(String password);

  SheetDefinition filter(Keywords.Auto auto);

  SheetDefinition page(Consumer<PageDefinition> pageDefinition);
}
