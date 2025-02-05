package nl.multicode.match;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("LIG3 Similarity Calculation Tests")
class LIG3Test {

    private LIG3 lig3;
    private LevenshteinDistance levenshteinDistance;

    @BeforeEach
    void setUp() {
        levenshteinDistance = new LevenshteinDistance();
        lig3 = new LIG3();
        lig3.levenshteinDistance = levenshteinDistance;
    }

    @ParameterizedTest
    @CsvSource({
            "hello, hello, 1.0",
            "hello, hillo, 0.9",
            "kitten, sitting, 0.75",
            "flaw, law, 0.0",
            "gumbo, gambio, 0.75",
            "intention, execution, 0.6"
    })
    @DisplayName("Given source and target strings, when computing LIG3 similarity, then correct score is returned")
    void givenStrings_whenComputingLIG3Similarity_thenCorrectScoreIsReturned(String src, String tar, double expected) {
        // Given
        // When
        double similarity = lig3.sim(src, tar);

        // Then
        assertThat(similarity).isCloseTo(expected, org.assertj.core.data.Offset.offset(0.05));
    }
}