package nl.multicode.match;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Clement Similarity Calculation Tests")
class ClementTest {

    private final Clement clement = new Clement();

    @ParameterizedTest
    @CsvSource({
            "hello world, hello world, 1.0",
            "'hello', 'world', 0.0",
            "'hello world', 'hello there', 0.16",
            "'apple banana', 'banana apple', 0.0",
            "'apple banana cherry', 'banana cherry date', 0.16",
            "'apple apple banana', 'apple banana banana', 0.0"
    })
    @DisplayName("Given source and target strings, when computing similarity, then correct score is returned")
    void givenStrings_whenComputingSimilarity_thenCorrectScoreIsReturned(String src, String tar, double expected) {
        // When
        double similarity = clement.sim(src, tar);

        // Then
        assertThat(similarity).isCloseTo(expected, Offset.offset(0.2));
    }
}
