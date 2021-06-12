package systems.defn.spreadsheet.query.poi;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import systems.defn.spreadsheet.query.api.SpreadsheetCriteria;
import systems.defn.spreadsheet.query.simple.SimpleSpreadsheetCriteria;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public enum PoiSpreadsheetCriteria {
  FACTORY;

  public SpreadsheetCriteria forWorkbook(Workbook workbook) {
    return SimpleSpreadsheetCriteria.forWorkbook(new PoiWorkbook(workbook));
  }

  public SpreadsheetCriteria forFile(File spreadsheet) throws FileNotFoundException {
    return forStream(new FileInputStream(spreadsheet));
  }

  public SpreadsheetCriteria forStream(InputStream stream) {
    try {
      return SimpleSpreadsheetCriteria.forWorkbook(new PoiWorkbook(WorkbookFactory.create(stream)));
    } catch (IOException e) {
      throw new RuntimeException("Exception creating new workbook: " + stream, e);
    }
  }
}
