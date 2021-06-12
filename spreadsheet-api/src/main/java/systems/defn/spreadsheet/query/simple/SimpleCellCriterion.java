package systems.defn.spreadsheet.query.simple;

import systems.defn.spreadsheet.api.Cell;
import systems.defn.spreadsheet.api.Comment;
import systems.defn.spreadsheet.query.api.CellCriterion;
import systems.defn.spreadsheet.query.api.CellStyleCriterion;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.function.Consumer;
import java.util.function.Predicate;

final class SimpleCellCriterion extends AbstractCriterion<Cell, CellCriterion> implements CellCriterion {

  SimpleCellCriterion() {}

  private SimpleCellCriterion(boolean disjoint) {
    super(disjoint);
  }

  @Override
  public SimpleCellCriterion date(final Date value) {
    addValueCondition(value, Date.class);
    return this;
  }

  @Override
  public SimpleCellCriterion date(final Predicate<Date> predicate) {
    addValueCondition(predicate, Date.class);
    return this;
  }

  @Override
  public CellCriterion localDate(LocalDate value) {
    addValueCondition(value, LocalDate.class);
    return this;
  }

  @Override
  public CellCriterion localDate(Predicate<LocalDate> predicate) {
    addValueCondition(predicate, LocalDate.class);
    return this;
  }

  @Override
  public CellCriterion localDateTime(LocalDateTime value) {
    addValueCondition(value, LocalDateTime.class);
    return this;
  }

  @Override
  public CellCriterion localDateTime(Predicate<LocalDateTime> predicate) {
    addValueCondition(predicate, LocalDateTime.class);
    return this;
  }

  @Override
  public CellCriterion localTime(LocalTime value) {
    addValueCondition(value, LocalTime.class);
    return this;
  }

  @Override
  public CellCriterion localTime(Predicate<LocalTime> predicate) {
    addValueCondition(predicate, LocalTime.class);
    return this;
  }

  @Override
  public SimpleCellCriterion number(Double value) {
    addValueCondition(value, Double.class);
    return this;
  }

  @Override
  public SimpleCellCriterion number(Predicate<Double> predicate) {
    addValueCondition(predicate, Double.class);
    return this;
  }

  @Override
  public SimpleCellCriterion string(String value) {
    addValueCondition(value, String.class);
    return this;
  }

  @Override
  public SimpleCellCriterion string(Predicate<String> predicate) {
    addValueCondition(predicate, String.class);
    return this;
  }

  @Override
  public SimpleCellCriterion value(Object value) {
    if (value == null) {
      string("");
      return this;
    }
    if (value instanceof Date) {
      date((Date) value);
      return this;
    }
    if (value instanceof Calendar) {
      date(((Calendar) value).getTime());
      return this;
    }
    if (value instanceof Number) {
      number(((Number) value).doubleValue());
      return this;
    }
    if (value instanceof Boolean) {
      bool((Boolean) value);
    }
    string(value.toString());
    return this;
  }

  @Override
  public SimpleCellCriterion name(final String name) {
    addCondition(o -> name.equals(o.getName()));
    return this;
  }

  @Override
  public SimpleCellCriterion comment(final String comment) {
    addCondition(o -> comment.equals(o.getComment().getText()));
    return this;
  }

  @Override
  public SimpleCellCriterion bool(Boolean value) {
    addValueCondition(value, Boolean.class);
    return this;
  }


  @Override
  public SimpleCellCriterion style(Consumer<CellStyleCriterion> styleCriterion) {
    SimpleCellStyleCriterion criterion = new SimpleCellStyleCriterion(this);
    styleCriterion.accept(criterion);
    // no need to add criteria, they are added by the style criterion itself
    return this;
  }

  @Override
  public SimpleCellCriterion rowSpan(final int span) {
    addCondition(o -> span == o.getRowSpan());
    return this;
  }

  @Override
  public SimpleCellCriterion rowSpan(final Predicate<Integer> predicate) {
    addCondition(o -> predicate.test(o.getRowSpan()));
    return this;
  }

  @Override
  public SimpleCellCriterion colSpan(final int span) {
    addCondition(o -> span == o.getColSpan());
    return this;
  }

  @Override
  public SimpleCellCriterion colSpan(final Predicate<Integer> predicate) {
    addCondition(o -> predicate.test(o.getColSpan()));
    return this;
  }

  @Override
  public SimpleCellCriterion name(final Predicate<String> predicate) {
    addCondition(o -> predicate.test(o.getName()));
    return this;
  }

  @Override
  public SimpleCellCriterion comment(final Predicate<Comment> predicate) {
    addCondition(o -> predicate.test(o.getComment()));
    return this;
  }

  private <T> void addValueCondition(final T value, final Class<T> type) {
    addCondition(o -> {
      try {
        return value.equals(o.read(type));
      } catch (Exception e) {
        return false;
      }
    });
  }

  private <T> void addValueCondition(final Predicate<T> predicate, final Class<T> type) {
    addCondition(o -> {
      try {
        return predicate.test(o.read(type));
      } catch (Exception e) {
        return false;
      }
    });
  }

  @Override
  public SimpleCellCriterion or(Consumer<CellCriterion> sheetCriterion) {
    return (SimpleCellCriterion) super.or(sheetCriterion);
  }

  @Override
  public CellCriterion having(Predicate<Cell> cellPredicate) {
    addCondition(cellPredicate);
    return this;
  }

  @Override
  CellCriterion newDisjointCriterionInstance() {
    return new SimpleCellCriterion(true);
  }
}
