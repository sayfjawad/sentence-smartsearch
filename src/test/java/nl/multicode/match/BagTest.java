package nl.multicode.match;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Bag Distance Calculation Tests")
class BagTest {

    private final Bag bag = new Bag();

    @ParameterizedTest
    @CsvSource({
            "hello world, hello world, 0",
            "'', hello, 5",
            "hello, '', 5",
            "apple banana, cherry date, 2",
            "apple banana cherry, banana cherry date, 1",
            "apple apple banana, apple banana banana, 1"
    })
    @DisplayName("Given source and target strings, when computing distance, then correct result is returned")
    void givenStrings_whenComputingDistance_thenCorrectResultIsReturned(String src, String tar, int expected) {
        // When
        int distance = bag.compute(src, tar);

        // Then
        assertThat(expected).isEqualTo(distance);
    }
}
