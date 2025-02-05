package nl.multicode.match;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Kuhns III Similarity Calculation Tests")
class KuhnsIIITest {

    private final KuhnsIII kuhnsIII = new KuhnsIII();

    @ParameterizedTest
    @CsvSource({
            "hello, hello, 0.25",
            "hello, hillo, 0.19",
            "kitten, sitting, 0.15",
            "colour, color, 0.25",
            "mister, mr, 0.25",
            "robert, rupert, 0.15",
            "brian, bryan, 0.21"
    })
    @DisplayName("Given source and target strings, when computing Kuhns III similarity, then correct score is returned")
    void givenStrings_whenComputingSimilarity_thenCorrectScoreIsReturned(String src, String tar, double expected) {
        // When
        double similarity = kuhnsIII.sim(src, tar);

        // Then
        assertThat(similarity).isCloseTo(expected, Offset.offset(0.05));
    }
}
