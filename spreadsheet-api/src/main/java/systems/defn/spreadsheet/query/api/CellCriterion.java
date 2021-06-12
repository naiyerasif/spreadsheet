package systems.defn.spreadsheet.query.api;

import systems.defn.spreadsheet.api.Cell;
import systems.defn.spreadsheet.api.Comment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.function.Consumer;
import java.util.function.Predicate;

public interface CellCriterion extends Predicate<Cell> {

  CellCriterion date(Date value);

  CellCriterion date(Predicate<Date> predicate);

  CellCriterion localDate(LocalDate value);

  CellCriterion localDate(Predicate<LocalDate> predicate);

  CellCriterion localDateTime(LocalDateTime value);

  CellCriterion localDateTime(Predicate<LocalDateTime> predicate);

  CellCriterion localTime(LocalTime value);

  CellCriterion localTime(Predicate<LocalTime> predicate);

  CellCriterion number(Double value);

  CellCriterion number(Predicate<Double> predicate);

  CellCriterion string(String value);

  CellCriterion string(Predicate<String> predicate);

  CellCriterion value(Object value);

  CellCriterion bool(Boolean value);

  CellCriterion style(Consumer<CellStyleCriterion> styleCriterion);

  CellCriterion rowSpan(int span);

  CellCriterion rowSpan(Predicate<Integer> predicate);

  CellCriterion colSpan(int span);

  CellCriterion colSpan(Predicate<Integer> predicate);

  CellCriterion name(String name);

  CellCriterion name(Predicate<String> predicate);

  CellCriterion comment(String comment);

  CellCriterion comment(Predicate<Comment> predicate);

  CellCriterion or(Consumer<CellCriterion> sheetCriterion);

  CellCriterion having(Predicate<Cell> cellPredicate);
}
