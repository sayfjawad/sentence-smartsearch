//package nl.multicode.search;
//
//import org.junit.jupiter.api.Test;
//
//import java.util.Arrays;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//class RatcliffObershelpSearchTest {
//
//
//    @Test
//    void testAlbertHeijnSimilaritySearch() {
//        RatcliffObershelpSearch search = new RatcliffObershelpSearch();
//
//        String searchSentence = "Albert Heijn";
//        List<String> sentences = Arrays.asList(
//                "Albört H ijn",
//                "Action",
//                "Blokker",
//                "H&M",
//                "Albert Heijn store",
//                "Alber Heijn"
//        );
//
//        // Choose a threshold based on expected Ratcliff-Obershelp similarity scores.
//        double threshold = 0.6;
//        List<String> similarSentences = search.findSimilarSentences(searchSentence, sentences, threshold);
//
//        // Expect that "Albört H ijn" and "Alber Heijn" are considered similar.
//        assertThat(similarSentences)
//                .contains("Albört H ijn")
//                .contains("Albert Heijn store")
//                .contains("Alber Heijn")
//                .hasSize(3);
//
//        similarSentences = search.search(searchSentence, sentences);
//        assertThat(similarSentences)
//                .contains("Albört H ijn")
//                .contains("Alber Heijn")
//                .contains("Albert Heijn store")
//                .hasSize(3);
//    }
//}
