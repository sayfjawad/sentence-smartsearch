package nl.multicode.match;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("FuzzyWuzzy Partial String Similarity Calculation Tests")
class FuzzyWuzzyPartialStringTest {

    private final FuzzyWuzzyPartialString fuzzyWuzzyPartialString = new FuzzyWuzzyPartialString();

    @ParameterizedTest
    @CsvSource({
            "hello, hello, 1.0",
            "hello, hillo, 0.8",
            "hello, yellow, 0.8",
            "test, testing, 1.0",
            "abc, abcd, 1.0",
            "apple, ample, 0.8",
            "fuzzy, buzzy, 0.8"
    })
    @DisplayName("Given source and target strings, when computing similarity, then correct score is returned")
    void givenStrings_whenComputingSimilarity_thenCorrectScoreIsReturned(String src, String tar, double expected) {
        // When
        double similarity = fuzzyWuzzyPartialString.sim(src, tar);

        // Then
        assertThat(similarity).isCloseTo(expected, Offset.offset(0.05));
    }
}