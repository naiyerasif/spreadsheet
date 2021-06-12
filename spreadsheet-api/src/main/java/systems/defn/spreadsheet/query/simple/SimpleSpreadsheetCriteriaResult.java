package systems.defn.spreadsheet.query.simple;

import systems.defn.spreadsheet.api.Cell;
import systems.defn.spreadsheet.api.Row;
import systems.defn.spreadsheet.api.Sheet;
import systems.defn.spreadsheet.api.Workbook;
import systems.defn.spreadsheet.query.api.AbstractSpreadsheetCriteriaResult;
import systems.defn.spreadsheet.query.api.WorkbookCriterion;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.function.Consumer;

final class SimpleSpreadsheetCriteriaResult extends AbstractSpreadsheetCriteriaResult {

  private final Workbook workbook;
  private final Consumer<WorkbookCriterion> workbookCriterion;
  private final int max;

  SimpleSpreadsheetCriteriaResult(Workbook workbook, Consumer<WorkbookCriterion> workbookCriterion, int max) {
    this.workbook = workbook;
    this.workbookCriterion = workbookCriterion;
    this.max = max;
  }

  private Collection<Cell> getCellsInternal(int currentMax) {
    Collection<Cell> cells = new LinkedHashSet<>();
    SimpleWorkbookCriterion criterion = new SimpleWorkbookCriterion();
    workbookCriterion.accept(criterion);

    for (Sheet sheet : workbook.getSheets()) {
      if (criterion.test(sheet)) {
        for (Row row : sheet.getRows()) {
          if (criterion.getCriteria().isEmpty()) {
            cells.addAll(row.getCells());
            if (cells.size() >= currentMax) {
              return cells;
            }
          } else {
            for (SimpleSheetCriterion sheetCriterion : criterion.getCriteria()) {
              if (sheetCriterion.test(row)) {
                if (sheetCriterion.getCriteria().isEmpty()) {
                  cells.addAll(row.getCells());
                  if (cells.size() >= currentMax) {
                    return cells;
                  }
                } else {
                  for (Cell cell : row.getCells()) {
                    for (SimpleRowCriterion rowCriterion : sheetCriterion.getCriteria()) {
                      if (rowCriterion.test(cell)) {
                        cells.add(cell);
                        if (cells.size() >= currentMax) {
                          return cells;
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }

    return cells;
  }

  private Collection<Row> getRowsInternal(int currentMax) {
    Collection<Row> rows = new LinkedHashSet<>();
    SimpleWorkbookCriterion criterion = new SimpleWorkbookCriterion();
    workbookCriterion.accept(criterion);

    for (Sheet sheet : workbook.getSheets()) {
      if (criterion.test(sheet)) {
        rows_loop:
        for (Row row : sheet.getRows()) {
          if (criterion.getCriteria().isEmpty()) {
            rows.add(row);
            if (rows.size() >= currentMax) {
              return rows;
            }
          } else {
            for (SimpleSheetCriterion sheetCriterion : criterion.getCriteria()) {
              if (sheetCriterion.test(row)) {
                if (sheetCriterion.getCriteria().isEmpty()) {
                  rows.add(row);
                  if (rows.size() >= currentMax) {
                    return rows;
                  }
                } else {
                  for (Cell cell : row.getCells()) {
                    for (SimpleRowCriterion rowCriterion : sheetCriterion.getCriteria()) {
                      if (rowCriterion.test(cell)) {
                        rows.add(row);
                        if (rows.size() >= currentMax) {
                          return rows;
                        }
                        continue rows_loop;
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }

    return rows;
  }

  private Collection<Sheet> getSheetsInternal(int currentMax) {
    Collection<Sheet> sheets = new LinkedHashSet<>();
    SimpleWorkbookCriterion criterion = new SimpleWorkbookCriterion();
    workbookCriterion.accept(criterion);

    sheets_loop:
    for (Sheet sheet : workbook.getSheets()) {
      if (criterion.test(sheet)) {
        if (sheet.getRows().isEmpty()) {
          sheets.add(sheet);
          if (sheets.size() >= currentMax) {
            return sheets;
          }
        } else {
          for (Row row : sheet.getRows()) {
            if (criterion.getCriteria().isEmpty()) {
              sheets.add(sheet);
              if (sheets.size() >= currentMax) {
                return sheets;
              }
            } else {
              for (SimpleSheetCriterion sheetCriterion : criterion.getCriteria()) {
                if (sheetCriterion.test(row)) {
                  if (sheetCriterion.getCriteria().isEmpty()) {
                    sheets.add(sheet);
                    if (sheets.size() >= currentMax) {
                      return sheets;
                    }
                  } else {
                    for (Cell cell : row.getCells()) {
                      for (SimpleRowCriterion rowCriterion : sheetCriterion.getCriteria()) {
                        if (rowCriterion.test(cell)) {
                          sheets.add(sheet);
                          if (sheets.size() >= currentMax) {
                            return sheets;
                          }
                          continue sheets_loop;
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }

    return sheets;
  }

  @Override
  public Collection<Cell> getCells() {
    return getCellsInternal(max);
  }

  @Override
  public Collection<Row> getRows() {
    return getRowsInternal(max);
  }

  @Override
  public Collection<Sheet> getSheets() {
    return getSheetsInternal(max);
  }

  @Override
  public Cell getCell() {
    Collection<Cell> cells = getCellsInternal(1);
    if (cells.size() > 0) {
      return cells.iterator().next();
    }
    return null;
  }

  @Override
  public Row getRow() {
    Collection<Row> rows = getRowsInternal(1);
    if (rows.size() > 0) {
      return rows.iterator().next();
    }
    return null;
  }

  @Override
  public Sheet getSheet() {
    Collection<Sheet> sheets = getSheetsInternal(1);
    if (sheets.size() > 0) {
      return sheets.iterator().next();
    }
    return null;
  }
}
