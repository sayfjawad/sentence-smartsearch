package nl.multicode.match;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("FuzzyWuzzy Token Set Similarity Calculation Tests")
class FuzzyWuzzyTokenSetTest {

    private final FuzzyWuzzyTokenSet fuzzyWuzzyTokenSet = new FuzzyWuzzyTokenSet();

    @ParameterizedTest
    @CsvSource({
            "hello world, hello world, 1.0",
            "hello world, world hello, 1.0",
            "hello, hillo, 0.8",
            "quick brown fox, the quick fox, 0.76",
            "apple banana, banana apple, 1.0",
            "java programming, programming in java, 1.0"
    })
    @DisplayName("Given source and target strings, when computing similarity, then correct score is returned")
    void givenStrings_whenComputingSimilarity_thenCorrectScoreIsReturned(String src, String tar, double expected) {
        // When
        double similarity = fuzzyWuzzyTokenSet.sim(src, tar);

        // Then
        assertThat(similarity).isCloseTo(expected, Offset.offset(0.05));
    }
}
