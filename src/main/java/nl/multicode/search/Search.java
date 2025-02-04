package nl.multicode.search;

import java.util.List;

public interface Search {

    List<String> search(String searchTerm, List<String> sentences);

    default String getAlgorithmName() {
        return this.getClass().getSimpleName().replace("Search", ""); // Remove "Search" suffix
    }
}
