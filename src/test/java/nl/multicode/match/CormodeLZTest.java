package nl.multicode.match;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Cormode LZ Distance Calculation Tests")
class CormodeLZTest {

    private final CormodeLZ cormodeLZ = new CormodeLZ();

    @ParameterizedTest
    @CsvSource({
            "hello world, hello world, 1.0",
            "'hello', 'world', 5.0",
            "'hello world', 'hello there', 7.0",
            "'apple banana', 'banana apple', 3.0",
            "'apple banana cherry', 'banana cherry date', 7.0",
            "'apple apple banana', 'apple banana banana', 2.0"
    })
    @DisplayName("Given source and target strings, when computing absolute distance, then correct value is returned")
    void givenStrings_whenComputingAbsoluteDistance_thenCorrectValueIsReturned(String src, String tar, double expected) {
        // When
        double distance = cormodeLZ.computeAbsoluteDistance(src, tar);

        // Then
        assertThat(distance).isCloseTo(expected, Offset.offset(0.5));
    }

    @ParameterizedTest
    @CsvSource({
            "hello world, hello world, 0.0",
            "'hello', 'world', 0.8",
            "'hello world', 'hello there', 0.54",
            "'apple banana', 'banana apple', 0.23",
            "'apple banana cherry', 'banana cherry date', 0.33",
            "'apple apple banana', 'apple banana banana', 0.055"
    })
    @DisplayName("Given source and target strings, when computing normalized distance, then correct value is returned")
    void givenStrings_whenComputingNormalizedDistance_thenCorrectValueIsReturned(String src, String tar, double expected) {
        // When
        double distance = cormodeLZ.computeNormalizedDistance(src, tar);

        // Then
        assertThat(distance).isCloseTo(expected, Offset.offset(0.1));
    }
}
