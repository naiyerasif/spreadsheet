package systems.defn.spreadsheet.builder.api;

import java.util.function.Consumer;

public interface HasStyle {

  HasStyle style(String name, Consumer<CellStyleDefinition> styleDefinition);

  HasStyle styles(Iterable<String> names, Consumer<CellStyleDefinition> styleDefinition);

  HasStyle styles(Iterable<String> styles, Iterable<Consumer<CellStyleDefinition>> styleDefinitions);

  HasStyle style(Consumer<CellStyleDefinition> styleDefinition);

  HasStyle style(String name);

  HasStyle styles(String... names);

  HasStyle styles(Iterable<String> names);
}
