package systems.defn.spreadsheet.builder.api;

import systems.defn.spreadsheet.api.Keywords;
import systems.defn.spreadsheet.api.PageSettingsProvider;

public interface PageDefinition extends PageSettingsProvider {

  PageDefinition orientation(Keywords.Orientation orientation);

  PageDefinition paper(Keywords.Paper paper);

  FitDimension fit(Keywords.Fit widthOrHeight);
}
