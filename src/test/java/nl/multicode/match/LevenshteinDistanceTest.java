package nl.multicode.match;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Levenshtein Distance Calculation Tests")
class LevenshteinDistanceTest {

    private final LevenshteinDistance levenshteinDistance = new LevenshteinDistance();

    @ParameterizedTest
    @CsvSource({
            "hello, hello, 0",
            "hello, hillo, 1",
            "kitten, sitting, 3",
            "flaw, law, 1",
            "gumbo, gambio, 2",
            "intention, execution, 5"
    })
    @DisplayName("Given source and target strings, when computing Levenshtein distance, then correct value is returned")
    void givenStrings_whenComputingLevenshteinDistance_thenCorrectValueIsReturned(String src, String tar, int expected) {
        // When
        int distance = levenshteinDistance.dist(src, tar);

        // Then
        assertThat(distance).isEqualTo(expected);
    }
}
