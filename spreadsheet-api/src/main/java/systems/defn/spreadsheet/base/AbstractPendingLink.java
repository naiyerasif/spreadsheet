package systems.defn.spreadsheet.base;

import systems.defn.spreadsheet.builder.api.CellDefinition;
import systems.defn.spreadsheet.builder.api.Resolvable;
import systems.defn.spreadsheet.function.NameSanitizer;

public abstract class AbstractPendingLink implements Resolvable {

  private final CellDefinition cell;
  private final String name;

  protected AbstractPendingLink(CellDefinition cell, final String name) {
    if (!name.equals(NameSanitizer.fixName(name))) {
      throw new IllegalArgumentException("Cannot call cell '" + name + "' as this is invalid identifier in Excel."
          + " Suggestion: " + NameSanitizer.fixName(name));
    }
    this.cell = cell;
    this.name = name;
  }

  public final CellDefinition getCell() {
    return cell;
  }

  public final String getName() {
    return name;
  }
}
