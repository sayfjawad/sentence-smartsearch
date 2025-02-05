package nl.multicode.match;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Indel Distance Calculation Tests")
class IndelTest {

    private final Indel indel = new Indel();

    @ParameterizedTest
    @CsvSource({
            "hello, hello, 0",
            "hello, hillo, 2",
            "kitten, sitting, 5",
            "abc, a, 2",
            "abcdef, azced, 5"
    })
    @DisplayName("Given source and target strings, when computing Indel distance, then correct value is returned")
    void givenStrings_whenComputingIndelDistance_thenCorrectValueIsReturned(String src, String tar, int expected) {
        // When
        int distance = indel.compute(src, tar);

        // Then
        assertThat(distance).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "hello, hello, 1.0",
            "hello, hillo, 0.6",
            "kitten, sitting, 0.28",
            "abc, a, 0.33",
            "abcdef, azced, 0.16"
    })
    @DisplayName("Given source and target strings, when computing normalized Indel similarity, then correct score is returned")
    void givenStrings_whenComputingNormalizedIndelSimilarity_thenCorrectScoreIsReturned(String src, String tar, double expected) {
        // When
        double similarity = indel.computeNormalized(src, tar);

        // Then
        assertThat(similarity).isCloseTo(expected, org.assertj.core.data.Offset.offset(0.05));
    }
}