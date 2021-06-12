package systems.defn.spreadsheet.api;

public interface CellStyle {

  Color getForeground();

  Color getBackground();

  ForegroundFill getFill();

  int getIndent();

  int getRotation();

  String getFormat();

  Font getFont();

  Border getBorder(Keywords.BorderSide borderSide);
}
