package systems.defn.spreadsheet.base;

import systems.defn.spreadsheet.api.BorderStyle;
import systems.defn.spreadsheet.api.Color;
import systems.defn.spreadsheet.api.Keywords;
import systems.defn.spreadsheet.builder.api.BorderDefinition;

public abstract class AbstractBorderDefinition implements BorderDefinition {

  protected BorderStyle borderStyle;

  @Override
  public final BorderDefinition style(BorderStyle style) {
    borderStyle = style;
    return this;
  }

  @Override
  public final BorderDefinition color(Color colorPreset) {
    color(colorPreset.getHex());
    return this;
  }

  protected abstract void applyTo(Keywords.BorderSide location);
}
