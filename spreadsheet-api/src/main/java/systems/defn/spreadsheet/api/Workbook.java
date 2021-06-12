package systems.defn.spreadsheet.api;

import java.util.Collection;

public interface Workbook {

  Collection<? extends Sheet> getSheets();
}
