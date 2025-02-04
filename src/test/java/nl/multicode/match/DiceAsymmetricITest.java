package nl.multicode.match;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Dice Asymmetric I Similarity Calculation Tests")
class DiceAsymmetricITest {

    private final DiceAsymmetricI diceAsymmetricI = new DiceAsymmetricI();

    @ParameterizedTest
    @CsvSource({
            "hello, hello, 1.0",
            "hello, world, 0.0",
            "hello, hella, 0.75",
            "banana, bandana, 1.0",
            "apples, apply, 0.6",
            "kitten, sitting, 0.4"
    })
    @DisplayName("Given source and target strings, when computing similarity, then correct score is returned")
    void givenStrings_whenComputingSimilarity_thenCorrectScoreIsReturned(String src, String tar, double expected) {
        // When
        double similarity = diceAsymmetricI.computeSimilarity(src, tar);

        // Then
        assertThat(similarity).isCloseTo(expected, Offset.offset(0.1));
    }
}
