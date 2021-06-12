package systems.defn.spreadsheet.base;

import systems.defn.spreadsheet.builder.api.CellStyleDefinition;
import systems.defn.spreadsheet.builder.api.Resolvable;
import systems.defn.spreadsheet.builder.api.SheetDefinition;
import systems.defn.spreadsheet.builder.api.WorkbookDefinition;
import systems.defn.spreadsheet.function.StringConverter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public abstract class AbstractWorkbookDefinition implements WorkbookDefinition {

  private final Map<String, Consumer<CellStyleDefinition>> namedStylesDefinition = new LinkedHashMap<>();
  private final Map<String, AbstractCellStyleDefinition> namedStyles = new LinkedHashMap<>();
  private final Map<String, AbstractSheetDefinition> sheets = new LinkedHashMap<>();
  private final List<Resolvable> toBeResolved = new ArrayList<>();

  @Override
  public final WorkbookDefinition style(String name, Consumer<CellStyleDefinition> styleDefinition) {
    namedStylesDefinition.put(name, styleDefinition);
    return this;
  }

  // TODO: make package private again
  public final void resolve() {
    for (Resolvable resolvable : toBeResolved) {
      resolvable.resolve();
    }
  }

  protected abstract AbstractCellStyleDefinition createCellStyle();

  protected abstract AbstractSheetDefinition createSheet(String name);

  @Override
  public final WorkbookDefinition sheet(String name, Consumer<SheetDefinition> sheetDefinition) {
    AbstractSheetDefinition sheet = sheets.get(name);

    if (sheet == null) {
      sheet = createSheet(name);
      sheets.put(name, sheet);
    }

    sheetDefinition.accept(sheet);

    sheet.resolve();

    return this;
  }

  final AbstractCellStyleDefinition getStyles(Iterable<String> names) {
    String name = StringConverter.join(names, ".");

    AbstractCellStyleDefinition style = namedStyles.get(name);

    if (style != null) {
      return style;
    }

    style = createCellStyle();
    for (String n : names) {
      getStyleDefinition(n).accept(style);
    }

    style.seal();

    namedStyles.put(name, style);

    return style;
  }

  final Consumer<CellStyleDefinition> getStyleDefinition(String name) {
    Consumer<CellStyleDefinition> style = namedStylesDefinition.get(name);
    if (style == null) {
      throw new IllegalArgumentException("Style '" + name + "' is not defined");
    }
    return style;
  }

  void addPendingFormula(AbstractPendingFormula formula) {
    toBeResolved.add(formula);
  }

  protected void addPendingLink(AbstractPendingLink link) {
    toBeResolved.add(link);
  }
}
