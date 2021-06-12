package systems.defn.spreadsheet.base;

import systems.defn.spreadsheet.api.Keywords;
import systems.defn.spreadsheet.builder.api.PageDefinition;
import systems.defn.spreadsheet.builder.api.Resolvable;
import systems.defn.spreadsheet.builder.api.RowDefinition;
import systems.defn.spreadsheet.builder.api.SheetDefinition;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public abstract class AbstractSheetDefinition implements SheetDefinition, Resolvable {

  private final AbstractWorkbookDefinition workbook;
  private final List<Integer> startPositions = new ArrayList<>();
  private int nextRowNumber;
  protected final Set<Integer> autoColumns = new HashSet<>();
  private final LinkedHashMap<Integer, AbstractRowDefinition> rows = new LinkedHashMap<>();
  protected boolean automaticFilter;

  protected AbstractSheetDefinition(AbstractWorkbookDefinition workbook) {
    this.workbook = workbook;
  }

  protected AbstractWorkbookDefinition getWorkbook() {
    return workbook;
  }

  private RowDefinition findOrCreateRow(int zeroBasedRowNumber) {
    AbstractRowDefinition row = rows.get(zeroBasedRowNumber + 1);

    if (row != null) {
      return row;
    }

    row = createRow(zeroBasedRowNumber);
    rows.put(zeroBasedRowNumber + 1, row);
    return row;
  }

  protected abstract AbstractRowDefinition createRow(int zeroBasedRowNumber);

  @Override
  public final SheetDefinition row(Consumer<RowDefinition> rowDefinition) {
    RowDefinition row = findOrCreateRow(nextRowNumber++);
    rowDefinition.accept(row);
    return this;
  }

  @Override
  public final SheetDefinition row(int oneBasedRowNumber, Consumer<RowDefinition> rowDefinition) {
    if (oneBasedRowNumber <= 0) {
      throw new IllegalArgumentException("Row index is based on 1. Got: " + oneBasedRowNumber);
    }
    nextRowNumber = oneBasedRowNumber;

    RowDefinition poiRow = findOrCreateRow(oneBasedRowNumber - 1);
    rowDefinition.accept(poiRow);
    return this;
  }

  @Override
  public final SheetDefinition freeze(int column, int row) {
    doFreeze(column, row);
    return this;
  }

  @Override
  public SheetDefinition state(Keywords.SheetState state) {
    switch (state) {
      case LOCKED -> {
        doLock();
        return this;
      }
      case VISIBLE -> {
        doShow();
        return this;
      }
      case HIDDEN -> {
        doHide();
        return this;
      }
      case VERY_HIDDEN -> {
        doHideCompletely();
        return this;
      }
    }
    throw new IllegalStateException("Unknown sheet state: " + state);
  }

  protected abstract void doHide();

  protected abstract void doHideCompletely();

  protected abstract void doShow();

  protected abstract void doLock();

  @Override
  public final SheetDefinition password(String password) {
    doPassword(password);
    return this;
  }

  protected abstract void doPassword(String password);

  protected abstract void doFreeze(int column, int row);

  @Override
  public final SheetDefinition collapse(Consumer<SheetDefinition> insideGroupDefinition) {
    createGroup(true, insideGroupDefinition);
    return this;
  }

  @Override
  public final SheetDefinition group(Consumer<SheetDefinition> insideGroupDefinition) {
    createGroup(false, insideGroupDefinition);
    return this;
  }


  @Override
  public final SheetDefinition filter(Keywords.Auto auto) {
    automaticFilter = true;
    return this;
  }

  @Override
  public final SheetDefinition page(Consumer<PageDefinition> pageDefinition) {
    PageDefinition page = createPageDefinition();
    pageDefinition.accept(page);
    return this;
  }

  protected abstract PageDefinition createPageDefinition();

  private void createGroup(boolean collapsed, Consumer<SheetDefinition> insideGroupDefinition) {
    startPositions.add(nextRowNumber);

    insideGroupDefinition.accept(this);

    int startPosition = startPositions.remove(startPositions.size() - 1);

    if (nextRowNumber - startPosition > 0) {
      int endPosition = nextRowNumber - 1;
      applyRowGroup(startPosition, endPosition, collapsed);
    }

  }

  protected abstract void applyRowGroup(int startPosition, int endPosition, boolean collapsed);

  // TODO: make protected again
  public void addAutoColumn(int i) {
    autoColumns.add(i);
  }

  protected abstract void processAutoColumns();

  protected abstract void processAutomaticFilter();

  @Override
  public String toString() {
    return "Sheet[" + getName() + "]";
  }

  protected abstract String getName();

  @Override
  public void resolve() {
    processAutomaticFilter();
    processAutoColumns();
  }
}
