//package nl.multicode.search;
//
//import org.junit.jupiter.api.Test;
//
//import java.util.Arrays;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//class FuzzyWuzzyTokenSetSearchTest {
//
//    @Test
//    void testAlbertHeijnSimilaritySearch() {
//        FuzzyWuzzyTokenSetSearch search = new FuzzyWuzzyTokenSetSearch();
//
//        String searchSentence = "Albert Heijn";
//        List<String> sentences = Arrays.asList(
//                "Albört H ijn",
//                "Action",
//                "Blokker",
//                "H&M",
//                "Albert Heijn store",
//                "Heijn Albert"
//        );
//
//        // Choose a threshold based on expected similarity scores.
//        double threshold = 0.5;
//        List<String> similarSentences = search.findSimilarSentences(searchSentence, sentences, threshold);
//
//        // Expect that "Albört H ijn" and "Heijn Albert" are considered similar.
//        assertThat(similarSentences)
//                .contains("Albert Heijn store")
//                .contains("Heijn Albert")
//                .hasSize(2);
//
//        similarSentences = search.search(searchSentence, sentences);
//        assertThat(similarSentences)
//                .contains("Albert Heijn store")
//                .contains("Heijn Albert")
//                .hasSize(2);
//    }
//}
//
