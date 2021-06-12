package systems.defn.spreadsheet.function;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class ColumnResolverTest {

  @ParameterizedTest
  @MethodSource("args")
  @DisplayName("Should match column with index")
  void shouldMatchColumnWithIndex(String column, int index) {
    assertThat(ColumnResolver.parseColumn(column)).isEqualTo(index);
    assertThat(ColumnResolver.toColumn(index)).isEqualTo(column);
  }

  private static Stream<Arguments> args() {
    return Stream.of(
        Arguments.of("A", 1),
        Arguments.of("B", 2),
        Arguments.of("Z", 26),
        Arguments.of("AA", 27),
        Arguments.of("AB", 28),
        Arguments.of("DA", 105)
    );
  }
}
