package nl.multicode.match;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Overlap Coëfficiënt Berekening Tests")
class OverlapTest {

    private final Overlap overlap = new Overlap();

    @ParameterizedTest
    @CsvSource({
            "hello, hello, 1.0",
            "hello, hillo, 0.8",
            "kitten, sitting, 0.6",
            "flaw, law, 1.0",
            "gumbo, gambio, 0.83",
            "intention, execution, 1.0"
    })
    @DisplayName("Gegeven bron- en doelstrings, wanneer de Overlap-coëfficiënt wordt berekend, dan wordt de juiste score geretourneerd")
    void gegevenStrings_wanneerOverlapCoefficientWordtBerekend_danWordtJuisteScoreGeretourneerd(String src, String tar, double expected) {
        // Wanneer
        double similarity = overlap.sim(src, tar);

        // Dan
        assertThat(similarity).isCloseTo(expected, Offset.offset(0.05));
    }
}