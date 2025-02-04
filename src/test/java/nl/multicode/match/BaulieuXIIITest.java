package nl.multicode.match;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Baulieu XIII Similarity Calculation Tests")
class BaulieuXIIITest {

    private final BaulieuXIII baulieuXIII = new BaulieuXIII();

    @ParameterizedTest
    @CsvSource({
            "hello world, hello world, 1.0",
            "'hello', 'world', 0.0",
            "'hello world', 'hello there', 0.83",
            "'apple banana', 'banana apple', 1.0",
            "'apple banana cherry', 'banana cherry date', 0.83",
            "'apple apple banana', 'apple banana banana', 0.8"
    })
    @DisplayName("Given source and target strings, when computing similarity, then correct score is returned")
    void givenStrings_whenComputingSimilarity_thenCorrectScoreIsReturned(String src, String tar, double expected) {
        // When
        double similarity = baulieuXIII.compute(src, tar);

        // Then
        assertThat(similarity).isCloseTo(expected, Offset.offset(0.2));
    }
}
