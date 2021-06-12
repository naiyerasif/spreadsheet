package systems.defn.spreadsheet.builder.api;

import systems.defn.spreadsheet.api.BorderStyle;
import systems.defn.spreadsheet.api.BorderStyleProvider;
import systems.defn.spreadsheet.api.Color;
import systems.defn.spreadsheet.api.ColorProvider;

public interface BorderDefinition extends BorderStyleProvider, ColorProvider {

  BorderDefinition style(BorderStyle style);

  BorderDefinition color(String hexColor);

  BorderDefinition color(Color color);
}
