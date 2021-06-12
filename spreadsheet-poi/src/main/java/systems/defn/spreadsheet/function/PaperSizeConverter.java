package systems.defn.spreadsheet.function;

import org.apache.poi.ss.usermodel.PrintSetup;
import systems.defn.spreadsheet.api.Keywords;

import java.util.HashMap;
import java.util.Map;

public final class PaperSizeConverter {

  private static final Map<Integer, Keywords.Paper> PAPER_SIZE_MAP = new HashMap<>();

  static {
    PAPER_SIZE_MAP.put(0, Keywords.Paper.PRINTER_DEFAULT);
    PAPER_SIZE_MAP.put(1, Keywords.Paper.LETTER);
    PAPER_SIZE_MAP.put(2, Keywords.Paper.LETTER_SMALL);
    PAPER_SIZE_MAP.put(3, Keywords.Paper.TABLOID);
    PAPER_SIZE_MAP.put(4, Keywords.Paper.LEDGER);
    PAPER_SIZE_MAP.put(5, Keywords.Paper.LEGAL);
    PAPER_SIZE_MAP.put(6, Keywords.Paper.STATEMENT);
    PAPER_SIZE_MAP.put(7, Keywords.Paper.EXECUTIVE);
    PAPER_SIZE_MAP.put(8, Keywords.Paper.A3);
    PAPER_SIZE_MAP.put(9, Keywords.Paper.A4);
    PAPER_SIZE_MAP.put(10, Keywords.Paper.A4_SMALL);
    PAPER_SIZE_MAP.put(11, Keywords.Paper.A5);
    PAPER_SIZE_MAP.put(12, Keywords.Paper.B4);
    PAPER_SIZE_MAP.put(13, Keywords.Paper.B5);
    PAPER_SIZE_MAP.put(14, Keywords.Paper.FOLIO);
    PAPER_SIZE_MAP.put(15, Keywords.Paper.QUARTO);
    PAPER_SIZE_MAP.put(16, Keywords.Paper.STANDARD_10_14);
    PAPER_SIZE_MAP.put(17, Keywords.Paper.STANDARD_11_17);
    PAPER_SIZE_MAP.put(18, Keywords.Paper.NOTE);
    PAPER_SIZE_MAP.put(19, Keywords.Paper.ENVELOPE_9);
    PAPER_SIZE_MAP.put(20, Keywords.Paper.ENVELOPE_10);
    PAPER_SIZE_MAP.put(27, Keywords.Paper.ENVELOPE_DL);
    PAPER_SIZE_MAP.put(28, Keywords.Paper.ENVELOPE_C5);
    PAPER_SIZE_MAP.put(29, Keywords.Paper.ENVELOPE_C3);
    PAPER_SIZE_MAP.put(30, Keywords.Paper.ENVELOPE_C4);
    PAPER_SIZE_MAP.put(31, Keywords.Paper.ENVELOPE_C6);
    PAPER_SIZE_MAP.put(37, Keywords.Paper.ENVELOPE_MONARCH);
    PAPER_SIZE_MAP.put(53, Keywords.Paper.A4_EXTRA);
    PAPER_SIZE_MAP.put(55, Keywords.Paper.A4_TRANSVERSE);
    PAPER_SIZE_MAP.put(60, Keywords.Paper.A4_PLUS);
    PAPER_SIZE_MAP.put(75, Keywords.Paper.LETTER_ROTATED);
    PAPER_SIZE_MAP.put(77, Keywords.Paper.A4_ROTATED);
  }

  private PaperSizeConverter() {}

  public static Keywords.Paper getPaper(PrintSetup printSetup) {
    return PAPER_SIZE_MAP.get((int) printSetup.getPaperSize());
  }
}
