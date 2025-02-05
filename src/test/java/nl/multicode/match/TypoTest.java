package nl.multicode.match;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Typo-Aware Edit Distance Calculation Tests")
class TypoTest {

    private final Typo typo = new Typo();

    @ParameterizedTest
    @CsvSource({
            "hello, hello, 1.0",
            "hello, hillo, 0.6",
            "kitten, sitten, 0.66",
            "flaw, law, 0.8",
            "gumbo, gambio, 0.5",
            "teh, the, 0.33"
    })
    @DisplayName("Gegeven bron- en doelstrings, wanneer de Typo-gelijkenis wordt berekend, dan wordt de juiste score geretourneerd")
    void gegevenStrings_wanneerTypoWordtBerekend_danWordtJuisteScoreGeretourneerd(String src, String tar, double expected) {
        // Wanneer
        double similarity = typo.sim(src, tar);

        // Dan
        assertThat(similarity).isCloseTo(expected, Offset.offset(0.05));
    }
}
