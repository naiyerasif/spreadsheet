package systems.defn.spreadsheet.query.simple;

import systems.defn.spreadsheet.api.Sheet;
import systems.defn.spreadsheet.query.api.SheetCriterion;
import systems.defn.spreadsheet.query.api.WorkbookCriterion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Consumer;

final class SimpleWorkbookCriterion extends AbstractCriterion<Sheet, WorkbookCriterion> implements WorkbookCriterion {

  private final Collection<SimpleSheetCriterion> criteria = new ArrayList<>();

  private SimpleWorkbookCriterion(boolean disjoint) {
    super(disjoint);
  }

  SimpleWorkbookCriterion() {}

  @Override
  public SimpleWorkbookCriterion sheet(final String name) {
    addCondition(o -> o.getName().equals(name));
    return this;
  }

  @Override
  public SimpleWorkbookCriterion sheet(final String name, Consumer<SheetCriterion> sheetCriterion) {
    sheet(name);
    sheet(sheetCriterion);
    return this;
  }

  @Override
  public SimpleWorkbookCriterion sheet(Consumer<SheetCriterion> sheetCriterion) {
    SimpleSheetCriterion sheet = new SimpleSheetCriterion(this);
    sheetCriterion.accept(sheet);
    criteria.add(sheet);
    return this;
  }

  @Override
  public SimpleWorkbookCriterion or(Consumer<WorkbookCriterion> sheetCriterion) {
    return (SimpleWorkbookCriterion) super.or(sheetCriterion);
  }

  Collection<SimpleSheetCriterion> getCriteria() {
    return Collections.unmodifiableCollection(criteria);
  }

  @Override
  WorkbookCriterion newDisjointCriterionInstance() {
    return new SimpleWorkbookCriterion(true);
  }
}
