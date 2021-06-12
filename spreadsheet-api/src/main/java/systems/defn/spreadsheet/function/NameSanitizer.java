package systems.defn.spreadsheet.function;

import java.util.regex.Pattern;

public final class NameSanitizer {

  private NameSanitizer() {}

  public static String fixName(String name) {
    if (name == null || name.length() == 0) {
      throw new IllegalArgumentException("Name cannot be null or empty!");
    }

    if (name.startsWith("c") || name.startsWith("C") || name.startsWith("r") || name.startsWith("R")) {
      return "_" + name;
    }

    name = name.replaceAll("[^.0-9a-zA-Z_]", "_");
    if (!Pattern.compile("^[abd-qs-zABD-QS-Z_].*").matcher(name).matches()) {
      return fixName("_" + name);
    }

    return name;
  }
}
