package systems.defn.spreadsheet.builder.api;

import systems.defn.spreadsheet.api.BorderPositionProvider;
import systems.defn.spreadsheet.api.Color;
import systems.defn.spreadsheet.api.ColorProvider;
import systems.defn.spreadsheet.api.ForegroundFill;
import systems.defn.spreadsheet.api.ForegroundFillProvider;
import systems.defn.spreadsheet.api.Keywords;

import java.util.function.Consumer;

public interface CellStyleDefinition extends ForegroundFillProvider, BorderPositionProvider, ColorProvider {

  CellStyleDefinition base(String styleName);

  CellStyleDefinition background(String hexColor);

  CellStyleDefinition background(Color color);

  CellStyleDefinition foreground(String hexColor);

  CellStyleDefinition foreground(Color color);

  CellStyleDefinition fill(ForegroundFill fill);

  CellStyleDefinition font(Consumer<systems.defn.spreadsheet.builder.api.FontDefinition> fontConfiguration);

  CellStyleDefinition indent(int indent);

  CellStyleDefinition wrap(Keywords.Text text);

  CellStyleDefinition rotation(int rotation);

  CellStyleDefinition format(String format);

  CellStyleDefinition align(Keywords.VerticalAlignment verticalAlignment,
      Keywords.HorizontalAlignment horizontalAlignment);

  CellStyleDefinition border(Consumer<BorderDefinition> borderConfiguration);

  CellStyleDefinition border(Keywords.BorderSide location, Consumer<BorderDefinition> borderConfiguration);

  CellStyleDefinition border(Keywords.BorderSide first, Keywords.BorderSide second,
      Consumer<BorderDefinition> borderConfiguration);

  CellStyleDefinition border(Keywords.BorderSide first, Keywords.BorderSide second, Keywords.BorderSide third,
      Consumer<BorderDefinition> borderConfiguration);
}
