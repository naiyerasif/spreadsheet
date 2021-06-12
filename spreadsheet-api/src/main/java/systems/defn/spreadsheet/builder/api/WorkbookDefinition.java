package systems.defn.spreadsheet.builder.api;

import java.util.function.Consumer;

public interface WorkbookDefinition extends CanDefineStyle {

  WorkbookDefinition sheet(String name, Consumer<SheetDefinition> sheetDefinition);

  WorkbookDefinition style(String name, Consumer<CellStyleDefinition> styleDefinition);

  default WorkbookDefinition apply(Class<? extends Stylesheet> stylesheet) {
    return (WorkbookDefinition) CanDefineStyle.super.apply(stylesheet);
  }

  default WorkbookDefinition apply(Stylesheet stylesheet) {
    stylesheet.declareStyles(this);
    return this;
  }
}
