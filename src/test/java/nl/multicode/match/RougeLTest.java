package nl.multicode.match;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Rouge-L Similarity Calculation Tests")
class RougeLTest {

    private final RougeL rougeL = new RougeL();

    @ParameterizedTest
    @CsvSource({
            "hello, hello, 1.0",
            "hello, hillo, 0.8",
            "kitten, sitting, 0.67",
            "flaw, law, 0.75",
            "gumbo, gambio, 0.83",
            "intention, execution, 0.55"
    })
    @DisplayName("Gegeven bron- en doelstrings, wanneer de Rouge-L gelijkenis wordt berekend, dan wordt de juiste score geretourneerd")
    void gegevenStrings_wanneerRougeLWordtBerekend_danWordtJuisteScoreGeretourneerd(String src, String tar, double expected) {
        // Wanneer
        double similarity = rougeL.sim(src, tar);

        // Dan
        assertThat(similarity).isCloseTo(expected, Offset.offset(0.05));
    }
}