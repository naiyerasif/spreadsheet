package systems.defn.spreadsheet.builder.api;

import systems.defn.spreadsheet.api.Keywords;

import java.util.Arrays;
import java.util.Collections;
import java.util.function.Consumer;

public interface CellDefinition extends HasStyle {

  CellDefinition value(Object value);

  CellDefinition name(String name);

  CellDefinition formula(String formula);

  default CellDefinition comment(final String commentText) {
    comment(commentDefinition -> commentDefinition.text(commentText));
    return this;
  }

  CellDefinition comment(Consumer<CommentDefinition> commentDefinition);

  LinkDefinition link(Keywords.To to);

  CellDefinition colSpan(int span);

  CellDefinition rowSpan(int span);

  DimensionModifier width(double width);

  DimensionModifier height(double height);

  CellDefinition width(Keywords.Auto auto);

  CellDefinition text(String text);

  CellDefinition text(String text, Consumer<FontDefinition> fontConfiguration);

  ImageCreator png(Keywords.Image image);

  ImageCreator jpeg(Keywords.Image image);

  ImageCreator pict(Keywords.Image image);

  ImageCreator emf(Keywords.Image image);

  ImageCreator wmf(Keywords.Image image);

  ImageCreator dib(Keywords.Image image);

  CellDefinition styles(Iterable<String> styles, Iterable<Consumer<CellStyleDefinition>> styleDefinitions);

  default CellDefinition style(String name) {
    return styles(Collections.singleton(name), Collections.emptyList());
  }

  default CellDefinition styles(String... names) {
    return styles(Arrays.asList(names), Collections.emptyList());
  }

  default CellDefinition style(Consumer<CellStyleDefinition> styleDefinition) {
    return styles(Collections.emptyList(), Collections.singleton(styleDefinition));
  }

  default CellDefinition styles(Iterable<String> names) {
    return styles(names, Collections.emptyList());
  }

  default CellDefinition style(String name, Consumer<CellStyleDefinition> styleDefinition) {
    return styles(Collections.singleton(name), Collections.singleton(styleDefinition));
  }

  default CellDefinition styles(Iterable<String> names, Consumer<CellStyleDefinition> styleDefinition) {
    return styles(names, Collections.singleton(styleDefinition));
  }
}
