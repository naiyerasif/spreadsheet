package systems.defn.spreadsheet.api;

public final class Keywords {

  public static final BorderStyle none = BorderStyle.NONE;
  public static final BorderStyle thin = BorderStyle.THIN;
  public static final BorderStyle medium = BorderStyle.MEDIUM;
  public static final BorderStyle dashed = BorderStyle.DASHED;
  public static final BorderStyle dotted = BorderStyle.DOTTED;
  public static final BorderStyle thick = BorderStyle.THICK;
  public static final BorderStyle doubleBorder = BorderStyle.DOUBLE;
  public static final BorderStyle hair = BorderStyle.HAIR;
  public static final BorderStyle mediumDashed = BorderStyle.MEDIUM_DASHED;
  public static final BorderStyle mashDot = BorderStyle.DASH_DOT;
  public static final BorderStyle mediumDashDot = BorderStyle.MEDIUM_DASH_DOT;
  public static final BorderStyle dashDotDot = BorderStyle.DASH_DOT_DOT;
  public static final BorderStyle mediumDashDotDot = BorderStyle.MEDIUM_DASH_DOT_DOT;
  public static final BorderStyle slantedDashDot = BorderStyle.SLANTED_DASH_DOT;
  public static final Orientation portrait = Orientation.PORTRAIT;
  public static final Orientation landscape = Orientation.LANDSCAPE;
  public static final Fit width = Fit.WIDTH;
  public static final Fit height = Fit.HEIGHT;
  public static final Paper letter = Paper.LETTER;
  public static final Paper letterSmall = Paper.LETTER_SMALL;
  public static final Paper tabloid = Paper.TABLOID;
  public static final Paper ledger = Paper.LEDGER;
  public static final Paper legal = Paper.LEGAL;
  public static final Paper statement = Paper.STATEMENT;
  public static final Paper executive = Paper.EXECUTIVE;
  public static final Paper A3 = Paper.A3;
  public static final Paper A4 = Paper.A4;
  public static final Paper A4Small = Paper.A4_SMALL;
  public static final Paper A5 = Paper.A5;
  public static final Paper B4 = Paper.B4;
  public static final Paper B5 = Paper.B5;
  public static final Paper folio = Paper.FOLIO;
  public static final Paper quarto = Paper.QUARTO;
  public static final Paper standard10x14 = Paper.STANDARD_10_14;
  public static final Paper standard11x17 = Paper.STANDARD_11_17;
  public static final FontStyle italic = FontStyle.ITALIC;
  public static final FontStyle bold = FontStyle.BOLD;
  public static final FontStyle strikeout = FontStyle.STRIKEOUT;
  public static final FontStyle underline = FontStyle.UNDERLINE;
  public static final ForegroundFill noFill = ForegroundFill.NO_FILL;
  public static final ForegroundFill solidForeground = ForegroundFill.SOLID_FOREGROUND;
  public static final ForegroundFill fineDots = ForegroundFill.FINE_DOTS;
  public static final ForegroundFill altBars = ForegroundFill.ALT_BARS;
  public static final ForegroundFill sparseDots = ForegroundFill.SPARSE_DOTS;
  public static final ForegroundFill thickHorizontalBands = ForegroundFill.THICK_HORZ_BANDS;
  public static final ForegroundFill thickVerticalBands = ForegroundFill.THICK_VERT_BANDS;
  public static final ForegroundFill thickBackwardDiagonals = ForegroundFill.THICK_BACKWARD_DIAG;
  public static final ForegroundFill thickForwardDiagonals = ForegroundFill.THICK_FORWARD_DIAG;
  public static final ForegroundFill bigSpots = ForegroundFill.BIG_SPOTS;
  public static final ForegroundFill bricks = ForegroundFill.BRICKS;
  public static final ForegroundFill thinHorizontalBands = ForegroundFill.THIN_HORZ_BANDS;
  public static final ForegroundFill thinVerticalBands = ForegroundFill.THIN_VERT_BANDS;
  public static final ForegroundFill thinBackwardDiagonals = ForegroundFill.THIN_BACKWARD_DIAG;
  public static final ForegroundFill thinForwardDiagonals = ForegroundFill.THICK_FORWARD_DIAG;
  public static final ForegroundFill squares = ForegroundFill.SQUARES;
  public static final ForegroundFill diamonds = ForegroundFill.DIAMONDS;
  public static final BorderSideAndHorizontalAlignment left = BorderSideAndHorizontalAlignment.LEFT;
  public static final BorderSideAndHorizontalAlignment right = BorderSideAndHorizontalAlignment.RIGHT;
  public static final BorderSideAndVerticalAlignment top = BorderSideAndVerticalAlignment.TOP;
  public static final BorderSideAndVerticalAlignment bottom = BorderSideAndVerticalAlignment.BOTTOM;
  public static final VerticalAndHorizontalAlignment center = VerticalAndHorizontalAlignment.CENTER;
  public static final VerticalAndHorizontalAlignment justify = VerticalAndHorizontalAlignment.JUSTIFY;
  public static final PureVerticalAlignment distributed = PureVerticalAlignment.DISTRIBUTED;
  public static final Text text = Text.WRAP;
  public static final Auto auto = Auto.AUTO;
  public static final To to = To.TO;
  public static final Image image = Image.IMAGE;
  public static final PureHorizontalAlignment general = PureHorizontalAlignment.GENERAL;
  public static final PureHorizontalAlignment fill = PureHorizontalAlignment.FILL;
  public static final PureHorizontalAlignment centerSelection = PureHorizontalAlignment.CENTER_SELECTION;
  public static final SheetState locked = SheetState.LOCKED;
  public static final SheetState visible = SheetState.VISIBLE;
  public static final SheetState hidden = SheetState.HIDDEN;
  public static final SheetState veryHidden = SheetState.VERY_HIDDEN;

  private Keywords() {}

  public enum Text {
    WRAP
  }

  public enum To {
    TO
  }

  public enum Image {
    IMAGE
  }

  public enum Auto {
    AUTO
  }

  public enum BorderSideAndVerticalAlignment implements BorderSide, VerticalAlignment {
    TOP,
    BOTTOM
  }

  public enum BorderSideAndHorizontalAlignment implements BorderSide, HorizontalAlignment {
    LEFT,
    RIGHT
  }

  public enum VerticalAndHorizontalAlignment implements VerticalAlignment, HorizontalAlignment {
    CENTER,
    JUSTIFY
  }

  public enum PureVerticalAlignment implements VerticalAlignment {
    DISTRIBUTED
  }

  public enum Orientation {
    LANDSCAPE,
    PORTRAIT
  }

  public enum Fit {
    HEIGHT,
    WIDTH
  }

  public enum Paper {
    PRINTER_DEFAULT,
    LETTER,
    LETTER_SMALL,
    TABLOID,
    LEDGER,
    LEGAL,
    STATEMENT,
    EXECUTIVE,
    A3,
    A4,
    A4_SMALL,
    A5,
    B4,
    B5,
    FOLIO,
    QUARTO,
    STANDARD_10_14,
    STANDARD_11_17,
    NOTE,
    ENVELOPE_9,
    ENVELOPE_10,
    ENVELOPE_DL,
    ENVELOPE_C5,
    ENVELOPE_C3,
    ENVELOPE_C4,
    ENVELOPE_C6,
    ENVELOPE_MONARCH,
    A4_EXTRA,
    A4_TRANSVERSE,
    A4_PLUS,
    LETTER_ROTATED,
    A4_ROTATED
  }

  public enum PureHorizontalAlignment implements HorizontalAlignment {
    GENERAL,
    FILL,
    CENTER_SELECTION
  }

  public enum SheetState {
    LOCKED,
    VISIBLE,
    HIDDEN,
    VERY_HIDDEN
  }

  public interface BorderSide {

    BorderSide LEFT = BorderSideAndHorizontalAlignment.LEFT;
    BorderSide RIGHT = BorderSideAndHorizontalAlignment.RIGHT;
    BorderSide TOP = BorderSideAndVerticalAlignment.TOP;
    BorderSide BOTTOM = BorderSideAndVerticalAlignment.BOTTOM;

    BorderSide[] BORDER_SIDES = { TOP, BOTTOM, LEFT, RIGHT };
  }

  public interface VerticalAlignment {

    VerticalAlignment TOP = BorderSideAndVerticalAlignment.TOP;
    VerticalAlignment CENTER = VerticalAndHorizontalAlignment.CENTER;
    VerticalAlignment BOTTOM = BorderSideAndVerticalAlignment.BOTTOM;
    VerticalAlignment JUSTIFY = VerticalAndHorizontalAlignment.JUSTIFY;
    VerticalAlignment DISTRIBUTED = PureVerticalAlignment.DISTRIBUTED;

    VerticalAlignment[] VERTICAL_ALIGNMENTS = { TOP, CENTER, BOTTOM, JUSTIFY, DISTRIBUTED };
  }

  public interface HorizontalAlignment {

    HorizontalAlignment RIGHT = BorderSideAndHorizontalAlignment.RIGHT;
    HorizontalAlignment LEFT = BorderSideAndHorizontalAlignment.LEFT;
    HorizontalAlignment GENERAL = PureHorizontalAlignment.GENERAL;
    HorizontalAlignment CENTER = VerticalAndHorizontalAlignment.CENTER;
    HorizontalAlignment FILL = PureHorizontalAlignment.FILL;
    HorizontalAlignment JUSTIFY = VerticalAndHorizontalAlignment.JUSTIFY;
    HorizontalAlignment CENTER_SELECTION = PureHorizontalAlignment.CENTER_SELECTION;

    HorizontalAlignment[] HORIZONTAL_ALIGNMENTS = { RIGHT, LEFT, GENERAL, CENTER, FILL, JUSTIFY, CENTER_SELECTION };
  }
}
