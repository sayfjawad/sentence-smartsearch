package nl.multicode.match;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Iterative-SubString (I-Sub) Similarity Calculation Tests")
class IterativeSubStringTest {

    private final IterativeSubString iterativeSubString = new IterativeSubString();

    @ParameterizedTest
    @CsvSource({
            "hello, hello, 1.0",
            "hello, hillo, 0.72",
            "kitten, sitting, 0.57",
            "colour, color, 0.87",
            "mister, mr, 0.049",
            "robert, rupert, 0.63",
            "brian, bryan, 0.092"
    })
    @DisplayName("Given source and target strings, when computing I-Sub similarity, then correct score is returned")
    void givenStrings_whenComputingSimilarity_thenCorrectScoreIsReturned(String src, String tar, double expected) {
        // When
        double similarity = iterativeSubString.sim(src, tar);

        // Then
        assertThat(similarity).isCloseTo(expected, Offset.offset(0.05));
    }
}
