package nl.multicode.match;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("String Subsequence Kernel (SSK) Similarity Calculation Tests")
class SSKTest {

    private final SSK ssk = new SSK();

    @ParameterizedTest
    @CsvSource({
            "hello, hello, 1.0",
            "hello, hillo, 0.52",
            "kitten, sitting, 0.36",
            "flaw, law, 0.80",
            "gumbo, gambio, 0.28",
            "intention, execution, 0.30"
    })
    @DisplayName("Gegeven bron- en doelstrings, wanneer de SSK-gelijkenis wordt berekend, dan wordt de juiste score geretourneerd")
    void gegevenStrings_wanneerSSKWordtBerekend_danWordtJuisteScoreGeretourneerd(String src, String tar, double expected) {
        // Wanneer
        double similarity = ssk.sim(src, tar);

        // Dan
        assertThat(similarity).isCloseTo(expected, Offset.offset(0.05));
    }
}