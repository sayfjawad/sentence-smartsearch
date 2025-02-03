package nl.multicode.search;

import java.util.List;

public interface Search {

    List<String> search(String searchTerm, List<String> sentences);
}
