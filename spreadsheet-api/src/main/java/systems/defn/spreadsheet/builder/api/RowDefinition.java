package systems.defn.spreadsheet.builder.api;

import systems.defn.spreadsheet.function.ColumnResolver;

import java.util.Arrays;
import java.util.Collections;
import java.util.function.Consumer;

public interface RowDefinition extends HasStyle {

  default RowDefinition cell() {
    return cell((Object) null);
  }

  default RowDefinition cell(Object value) {
    return cell(c -> c.value(value));
  }

  RowDefinition cell(Consumer<CellDefinition> cellDefinition);

  RowDefinition cell(int column, Consumer<CellDefinition> cellDefinition);

  default RowDefinition cell(String column, Consumer<CellDefinition> cellDefinition) {
    return cell(ColumnResolver.parseColumn(column), cellDefinition);
  }

  RowDefinition group(Consumer<RowDefinition> insideGroupDefinition);

  RowDefinition collapse(Consumer<RowDefinition> insideGroupDefinition);

  RowDefinition styles(Iterable<String> styles, Iterable<Consumer<CellStyleDefinition>> styleDefinitions);

  default RowDefinition style(Consumer<CellStyleDefinition> styleDefinition) {
    return styles(Collections.emptyList(), Collections.singleton(styleDefinition));
  }

  default RowDefinition style(String name) {
    return styles(Collections.singleton(name), Collections.emptyList());
  }

  default RowDefinition style(String name, Consumer<CellStyleDefinition> styleDefinition) {
    return styles(Collections.singleton(name), styleDefinition);
  }

  default RowDefinition styles(Iterable<String> names, Consumer<CellStyleDefinition> styleDefinition) {
    return styles(names, Collections.singleton(styleDefinition));
  }

  default RowDefinition styles(String... names) {
    return styles(Arrays.asList(names));
  }

  default RowDefinition styles(Iterable<String> names) {
    return styles(names, Collections.emptyList());
  }
}
