package systems.defn.spreadsheet.base;

import systems.defn.spreadsheet.api.Keywords;
import systems.defn.spreadsheet.api.Spannable;
import systems.defn.spreadsheet.builder.api.CellDefinition;
import systems.defn.spreadsheet.builder.api.CellStyleDefinition;
import systems.defn.spreadsheet.builder.api.CommentDefinition;
import systems.defn.spreadsheet.builder.api.FontDefinition;
import systems.defn.spreadsheet.builder.api.LinkDefinition;
import systems.defn.spreadsheet.builder.api.Resolvable;
import systems.defn.spreadsheet.function.NameSanitizer;
import systems.defn.spreadsheet.function.StringConverter;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

public abstract class AbstractCellDefinition implements CellDefinition, Resolvable, Spannable {

  private final AbstractRowDefinition row;
  private int colSpan = 1;
  private int rowSpan = 1;
  protected AbstractCellStyleDefinition cellStyle;
  protected List<RichTextPart> richTextParts = new ArrayList<>();

  protected AbstractCellDefinition(AbstractRowDefinition row) {
    this.row = Objects.requireNonNull(row, "Row");
  }

  @Override
  public final CellDefinition formula(String formula) {
    row.getSheet().getWorkbook().addPendingFormula(createPendingFormula(formula));
    return this;
  }

  protected abstract AbstractPendingFormula createPendingFormula(String formula);

  @Override
  public final CellDefinition comment(Consumer<CommentDefinition> commentDefinition) {
    DefaultCommentDefinition poiComment = new DefaultCommentDefinition();
    commentDefinition.accept(poiComment);
    applyComment(poiComment);
    return this;
  }

  @Override
  public final CellDefinition colSpan(int span) {
    this.colSpan = span;
    return this;
  }

  @Override
  public final CellDefinition rowSpan(int span) {
    this.rowSpan = span;
    return this;
  }

  @Override
  public final CellDefinition styles(Iterable<String> names, Iterable<Consumer<CellStyleDefinition>> styleDefinition) {
    if (styleDefinition == null || !styleDefinition.iterator().hasNext()) {
      if (names == null || !names.iterator().hasNext()) {
        return this;
      }

      Set<String> allNames = new LinkedHashSet<>();
      for (String name : names) {
        allNames.add(name);
      }
      allNames.addAll(row.getStyles());

      if (cellStyle == null) {
        cellStyle = row.getSheet().getWorkbook().getStyles(allNames);
        assignStyle(cellStyle);
        return this;
      }

      if (cellStyle.isSealed()) {
        if (!row.getStyles().isEmpty()) {
          cellStyle = null;
          styles(allNames);
          return this;
        }
      } else {
        for (String name : names) {
          row.getSheet().getWorkbook().getStyleDefinition(name).accept(cellStyle);
        }
      }
      return this;
    }

    if (cellStyle == null) {
      cellStyle = createCellStyle();
    }

    if (cellStyle.isSealed()) {
      throw new IllegalStateException("The cell style '" + StringConverter.join(names, ".")
          + "' is already sealed! You need to create new style. Use 'styles' method to combine multiple named"
          + " styles! Create new named style if you're trying to update existing style with closure definition.");
    }

    for (String name : names) {
      row.getSheet().getWorkbook().getStyleDefinition(name).accept(cellStyle);
    }

    for (Consumer<CellStyleDefinition> configurer : styleDefinition) {
      configurer.accept(cellStyle);
    }

    return this;
  }

  protected abstract AbstractCellStyleDefinition createCellStyle();

  protected abstract void assignStyle(CellStyleDefinition cellStyle);

  @Override
  public final CellDefinition name(final String name) {
    if (!NameSanitizer.fixName(name).equals(name)) {
      throw new IllegalArgumentException(
          "Name " + name + " is not valid Excel name! Suggestion: " + NameSanitizer.fixName(name));
    }

    doName(name);
    return this;
  }

  protected abstract void doName(String name);

  @Override
  public final LinkDefinition link(Keywords.To to) {
    return createLinkDefinition();
  }

  protected abstract LinkDefinition createLinkDefinition();

  @Override
  public final CellDefinition text(String run) {
    text(run, null);
    return this;
  }

  @Override
  public final CellDefinition text(String run, Consumer<FontDefinition> fontConfiguration) {
    if (run == null || run.length() == 0) {
      return this;
    }

    int start = 0;
    if (richTextParts != null && richTextParts.size() > 0) {
      start = richTextParts.get(richTextParts.size() - 1).getEnd();
    }

    int end = start + run.length();

    if (fontConfiguration == null) {
      richTextParts.add(new RichTextPart(run, null, start, end));
      return this;
    }

    FontDefinition font = createFontDefinition();
    fontConfiguration.accept(font);

    richTextParts.add(new RichTextPart(run, font, start, end));
    return this;
  }

  protected abstract FontDefinition createFontDefinition();

  public final int getColSpan() {
    return colSpan;
  }

  public final int getRowSpan() {
    return rowSpan;
  }

  protected abstract void applyComment(DefaultCommentDefinition comment);

  public AbstractRowDefinition getRow() {
    return row;
  }
}
