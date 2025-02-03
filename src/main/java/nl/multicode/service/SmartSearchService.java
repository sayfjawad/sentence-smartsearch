package nl.multicode.service;


import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.enterprise.inject.Any;
import jakarta.enterprise.inject.Instance;
import nl.multicode.search.Search;

@ApplicationScoped
public class SmartSearchService {

    @Any
    Instance<Search> searchAlgorithms;

    public List<String> performSearch(final String input, final List<String> sentences) {
        return searchAlgorithms.stream()
                .flatMap(algorithm -> algorithm.search(input, sentences).stream())
                .collect(Collectors.toList());
    }
}
