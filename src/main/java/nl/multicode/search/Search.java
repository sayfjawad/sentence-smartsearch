package nl.multicode.search;

import java.util.List;
import java.util.Map;

public interface Search {
    String getAlgorithmName();
    List<String> search(String searchTerm, List<String> sentences);
    List<Map.Entry<String, Double>> searchWithScores(String searchTerm, List<String> sentences);
}
