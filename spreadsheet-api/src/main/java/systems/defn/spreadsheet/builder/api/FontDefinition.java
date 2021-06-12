package systems.defn.spreadsheet.builder.api;

import systems.defn.spreadsheet.api.Color;
import systems.defn.spreadsheet.api.ColorProvider;
import systems.defn.spreadsheet.api.FontStyle;
import systems.defn.spreadsheet.api.FontStylesProvider;

public interface FontDefinition extends FontStylesProvider, ColorProvider {

  FontDefinition color(String hexColor);

  FontDefinition color(Color color);

  FontDefinition size(int size);

  FontDefinition name(String name);

  FontDefinition style(FontStyle first, FontStyle... other);
}
