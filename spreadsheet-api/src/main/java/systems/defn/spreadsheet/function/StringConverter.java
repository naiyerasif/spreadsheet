package systems.defn.spreadsheet.function;

import java.util.Objects;
import java.util.StringJoiner;

public final class StringConverter {

  private StringConverter() {}

  public static String join(Iterable<String> strings, String separator) {
    var delimiter = Objects.isNull(separator) ? "" : separator;
    final StringJoiner joiner = new StringJoiner(delimiter);
    strings.forEach(joiner::add);
    return joiner.toString();
  }
}
