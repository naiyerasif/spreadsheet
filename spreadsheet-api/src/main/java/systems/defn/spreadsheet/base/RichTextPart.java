package systems.defn.spreadsheet.base;

import systems.defn.spreadsheet.builder.api.FontDefinition;

public final class RichTextPart {

  private final String text;
  private final FontDefinition font;
  private final int start;
  private final int end;

  public RichTextPart(String text, FontDefinition font, int start, int end) {
    this.text = text;
    this.font = font;
    this.start = start;
    this.end = end;
  }

  public final String getText() {
    return text;
  }

  public final FontDefinition getFont() {
    return font;
  }

  public final int getStart() {
    return start;
  }

  public final int getEnd() {
    return end;
  }
}
