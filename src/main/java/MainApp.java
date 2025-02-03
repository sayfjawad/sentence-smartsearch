import nl.multicode.match.LevenshteinDistance;
import nl.multicode.search.LevenshteinSimilaritySearch;

import java.util.Arrays;
import java.util.List;

public class MainApp {

    public static void main(String[] args) {

        LevenshteinSimilaritySearch autoGrouper = new LevenshteinSimilaritySearch(new LevenshteinDistance());

        String searchSentence = "Albert Heijn";
        List<String> sentences = Arrays.asList(
                "Albört H ijn",
                "Action",
                "Blokker",
                "H&M",
                "Albert Heijn store",
                "Alber Heijn"
        );

        int threshold = 3;
        List<String> similarSentences = autoGrouper.findSimilarSentences(searchSentence, sentences, threshold);

        System.out.println("Similar sentences: " + similarSentences);
    }
}
