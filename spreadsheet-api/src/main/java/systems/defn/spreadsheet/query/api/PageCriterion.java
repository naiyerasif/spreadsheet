package systems.defn.spreadsheet.query.api;

import systems.defn.spreadsheet.api.Keywords;
import systems.defn.spreadsheet.api.Page;
import systems.defn.spreadsheet.api.PageSettingsProvider;

import java.util.function.Predicate;

public interface PageCriterion extends PageSettingsProvider {

  PageCriterion orientation(Keywords.Orientation orientation);

  PageCriterion paper(Keywords.Paper paper);

  PageCriterion having(Predicate<Page> pagePredicate);
}
