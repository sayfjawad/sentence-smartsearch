package nl.multicode.match;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Editex Similarity Calculation Tests")
class EditexTest {

    private final Editex editex = new Editex();

    @ParameterizedTest
    @CsvSource({
            "hello, hello, 1.0",
            "hello, hillo, 0.8",
            "knight, night, 0.85",
            "colour, color, 0.83",
            "mister, mr, 0.33",
            "robert, rupert, 0.66",
            "brian, bryan, 0.8"
    })
    @DisplayName("Given source and target strings, when computing similarity, then correct score is returned")
    void givenStrings_whenComputingSimilarity_thenCorrectScoreIsReturned(String src, String tar, double expected) {
        // When
        double similarity = editex.sim(src, tar);

        // Then
        assertThat(similarity).isCloseTo(expected, Offset.offset(0.05));
    }
}