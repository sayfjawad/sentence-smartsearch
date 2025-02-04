//package nl.multicode.search;
//
//import org.junit.jupiter.api.Test;
//
//import java.util.Arrays;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//class OverlapSearchTest {
//
//    @Test
//    void testAlbertHeinSimilaritySearch() {
//        OverlapSearch search = new OverlapSearch();
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
//        // Kies een drempelwaarde gebaseerd op verwachte Overlap-similariteitsscores.
//        double threshold = 0.7;
//        List<String> similarSentences = search.findSimilarSentences(searchSentence, sentences, threshold);
//
//        // Verwacht dat alleen "Albört H ijn" en "Alber Heijn" als vergelijkbaar worden beschouwd.
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
//
//}
