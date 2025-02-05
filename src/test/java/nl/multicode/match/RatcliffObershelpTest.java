package nl.multicode.match;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Ratcliff-Obershelp Similarity Calculation Tests")
class RatcliffObershelpTest {

    private final RatcliffObershelp ratcliffObershelp = new RatcliffObershelp();

    @ParameterizedTest
    @CsvSource({
            "hello, hello, 1.0",
            "hello, hillo, 0.8",
            "kitten, sitting, 0.61",
            "flaw, law, 0.85",
            "gumbo, gambio, 0.72",
            "intention, execution, 0.55"
    })
    @DisplayName("Gegeven bron- en doelstrings, wanneer de Ratcliff-Obershelp gelijkenis wordt berekend, dan wordt de juiste score geretourneerd")
    void gegevenStrings_wanneerRatcliffObershelpWordtBerekend_danWordtJuisteScoreGeretourneerd(String src, String tar, double expected) {
        // Wanneer
        double similarity = ratcliffObershelp.sim(src, tar);

        // Dan
        assertThat(similarity).isCloseTo(expected, Offset.offset(0.05));
    }
}
