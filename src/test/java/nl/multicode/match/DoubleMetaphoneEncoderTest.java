package nl.multicode.match;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Double Metaphone Encoder Tests")
class DoubleMetaphoneEncoderTest {

    private final DoubleMetaphoneEncoder encoder = new DoubleMetaphoneEncoder();

    @ParameterizedTest
    @CsvSource({
            "Smith, SM0",
            "Smyth, SM0",
            "Knight, NT",
            "Nite, NT",
            "Schmidt, XMT",
            "Shmit, XMT",
            "Robert, RPRT",
            "Rupert, RPRT"
    })
    @DisplayName("Given a word, when encoding, then primary Double Metaphone code is returned")
    void givenWord_whenEncoding_thenPrimaryMetaphoneCodeIsReturned(String word, String expected) {
        // When
        String encoded = encoder.encode(word);

        // Then
        assertThat(encoded).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "Smith, Smyth, true",
            "Knight, Nite, true",
            "Robert, Rupert, true",
            "Schmidt, Shmit, true",
            "Apple, Orange, false",
            "Gough, Go, false"
    })
    @DisplayName("Given two words, when checking phonetic match, then correct boolean is returned")
    void givenTwoWords_whenCheckingPhoneticMatch_thenCorrectBooleanIsReturned(String word1, String word2, boolean expected) {
        // When
        boolean match = encoder.isPhoneticMatch(word1, word2);

        // Then
        assertThat(match).isEqualTo(expected);
    }
}
