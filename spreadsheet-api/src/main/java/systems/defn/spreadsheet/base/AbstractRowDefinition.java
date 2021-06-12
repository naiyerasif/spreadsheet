package systems.defn.spreadsheet.base;

import systems.defn.spreadsheet.builder.api.CellDefinition;
import systems.defn.spreadsheet.builder.api.CellStyleDefinition;
import systems.defn.spreadsheet.builder.api.RowDefinition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public abstract class AbstractRowDefinition implements RowDefinition {

  protected final AbstractSheetDefinition sheet;
  private List<String> styles = new ArrayList<>();
  private List<Consumer<CellStyleDefinition>> styleDefinitions = new ArrayList<>();
  private final List<Integer> startPositions = new ArrayList<>();
  private int nextColNumber;
  private final Map<Integer, AbstractCellDefinition> cells = new LinkedHashMap<>();

  protected AbstractRowDefinition(AbstractSheetDefinition sheet) {
    this.sheet = sheet;
  }

  private AbstractCellDefinition findOrCreateCell(int zeroBasedCellNumber) {
    AbstractCellDefinition cell = cells.get(zeroBasedCellNumber + 1);

    if (cell != null) {
      return cell;
    }

    cell = createCell(zeroBasedCellNumber);

    cells.put(zeroBasedCellNumber + 1, cell);

    return cell;
  }

  protected abstract AbstractCellDefinition createCell(int zeroBasedCellNumber);

  @Override
  public final RowDefinition cell(Consumer<CellDefinition> cellDefinition) {
    AbstractCellDefinition poiCell = findOrCreateCell(nextColNumber);

    if (!styles.isEmpty() || !styleDefinitions.isEmpty()) {
      poiCell.styles(styles, styleDefinitions);
    }

    cellDefinition.accept(poiCell);

    nextColNumber += poiCell.getColSpan();

    handleSpans(poiCell);

    poiCell.resolve();

    return this;
  }

  protected abstract void handleSpans(AbstractCellDefinition poiCell);

  @Override
  public final RowDefinition cell(int column, Consumer<CellDefinition> cellDefinition) {
    AbstractCellDefinition poiCell = findOrCreateCell(column - 1);

    if (!styles.isEmpty() || !styleDefinitions.isEmpty()) {
      poiCell.styles(styles, styleDefinitions);
    }

    cellDefinition.accept(poiCell);

    nextColNumber = column - 1 + poiCell.getColSpan();

    handleSpans(poiCell);

    poiCell.resolve();

    return this;
  }

  @Override
  public final RowDefinition styles(Iterable<String> styles, Iterable<Consumer<CellStyleDefinition>> styleDefinitions) {
    for (String name : styles) {
      this.styles.add(name);
    }
    for (Consumer<CellStyleDefinition> style : styleDefinitions) {
      this.styleDefinitions.add(style);
    }
    return this;
  }

  AbstractSheetDefinition getSheet() {
    return sheet;
  }

  @Override
  public final RowDefinition group(Consumer<RowDefinition> insideGroupDefinition) {
    createGroup(false, insideGroupDefinition);
    return this;
  }

  @Override
  public final RowDefinition collapse(Consumer<RowDefinition> insideGroupDefinition) {
    createGroup(true, insideGroupDefinition);
    return this;
  }


  private void createGroup(boolean collapsed, Consumer<RowDefinition> insideGroupDefinition) {
    startPositions.add(nextColNumber);
    insideGroupDefinition.accept(this);

    int startPosition = startPositions.remove(startPositions.size() - 1);

    if (nextColNumber - startPosition > 0) {
      int endPosition = nextColNumber - 1;
      doCreateGroup(startPosition, endPosition, collapsed);
    }
  }

  protected abstract void doCreateGroup(int startPosition, int endPosition, boolean collapsed);

  @Override
  public String toString() {
    return "Row[" + sheet.getName() + "!" + getNumber() + "]";
  }

  protected abstract int getNumber();

  List<String> getStyles() {
    return Collections.unmodifiableList(styles);
  }
}
