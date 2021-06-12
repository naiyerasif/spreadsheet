package systems.defn.spreadsheet.query.simple;

import systems.defn.spreadsheet.api.Cell;
import systems.defn.spreadsheet.api.Row;
import systems.defn.spreadsheet.function.ColumnResolver;
import systems.defn.spreadsheet.query.api.CellCriterion;
import systems.defn.spreadsheet.query.api.RowCriterion;

import java.util.function.Consumer;
import java.util.function.Predicate;

final class SimpleRowCriterion extends AbstractCriterion<Cell, RowCriterion> implements RowCriterion {

  private final SimpleSheetCriterion parent;

  SimpleRowCriterion(SimpleSheetCriterion parent) {
    this.parent = parent;
  }

  private SimpleRowCriterion(SimpleSheetCriterion parent, boolean disjoint) {
    super(disjoint);
    this.parent = parent;
  }

  @Override
  public RowCriterion cell(final int from, final int to) {
    addCondition(o -> o.getColumn() >= from && o.getColumn() <= to);
    return this;
  }

  @Override
  public RowCriterion cell(String from, String to) {
    cell(ColumnResolver.parseColumn(from), ColumnResolver.parseColumn(to));
    return this;
  }

  @Override
  public RowCriterion cell(int from, int to, Consumer<CellCriterion> cellCriterion) {
    cell(from, to);
    cell(cellCriterion);
    return this;
  }

  @Override
  public RowCriterion cell(String from, String to, Consumer<CellCriterion> cellCriterion) {
    cell(from, to);
    cell(cellCriterion);
    return this;
  }

  @Override
  public SimpleRowCriterion cell(final int column) {
    addCondition(o -> o.getColumn() == column);
    return this;
  }

  @Override
  public SimpleRowCriterion cell(final String column) {
    addCondition(o -> o.getColumnAsString().equals(column));
    return this;
  }

  @Override
  public SimpleRowCriterion cell(Consumer<CellCriterion> cellCriterion) {
    SimpleCellCriterion criterion = new SimpleCellCriterion();
    cellCriterion.accept(criterion);
    addCondition(criterion);
    return this;
  }

  @Override
  public SimpleRowCriterion cell(int column, Consumer<CellCriterion> cellCriterion) {
    cell(column);
    cell(cellCriterion);
    return this;
  }

  @Override
  public SimpleRowCriterion cell(String column, Consumer<CellCriterion> cellCriterion) {
    cell(column);
    cell(cellCriterion);
    return this;
  }

  @Override
  public SimpleRowCriterion or(Consumer<RowCriterion> sheetCriterion) {
    return (SimpleRowCriterion) super.or(sheetCriterion);
  }

  @Override
  public RowCriterion having(Predicate<Row> rowPredicate) {
    parent.addCondition(rowPredicate);
    return this;
  }

  @Override
  RowCriterion newDisjointCriterionInstance() {
    return new SimpleRowCriterion(parent, true);
  }
}
