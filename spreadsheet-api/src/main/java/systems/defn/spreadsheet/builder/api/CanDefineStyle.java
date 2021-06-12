package systems.defn.spreadsheet.builder.api;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Consumer;

public interface CanDefineStyle {

  CanDefineStyle style(String name, Consumer<CellStyleDefinition> styleDefinition);

  default CanDefineStyle apply(Class<? extends Stylesheet> stylesheet) {
    try {
      apply(stylesheet.getDeclaredConstructor().newInstance());
    } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
      throw new IllegalArgumentException("Cannot create new instance of " + stylesheet, e);
    }
    return this;
  }

  CanDefineStyle apply(Stylesheet stylesheet);
}
