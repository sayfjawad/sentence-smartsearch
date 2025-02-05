package nl.multicode.match;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Refined Soundex Encoding Tests")
class RefinedSoundexTest {

    private final RefinedSoundex refinedSoundex = new RefinedSoundex();

    @ParameterizedTest
    @CsvSource({
            "Robert, R196",
            "Rupert, R196",
            "Rubin, R18",
            "Ashcraft, A33926",
            "Tymczak, T8353",
            "Pfister, P2369",
            "Honeyman, H888"
    })
    @DisplayName("Given a word, when encoding, then the correct Refined Soundex code is returned")
    void givenWord_whenEncoding_thenCorrectRefinedSoundexCodeIsReturned(String word, String expected) {
        // When
        String encoded = refinedSoundex.encode(word);

        // Then
        assertThat(encoded).isEqualTo(expected);
    }
}
