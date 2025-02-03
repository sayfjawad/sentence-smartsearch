package nl.multicode.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.List;

@Schema(description = "Data structure for search operation")
public class SearchData {

    @Schema(description = "Search term to look for", example = "Albert Heijn")
    private String search;

    @Schema(description = "List of sentences to search in", example = "[\"Albert Heijn store\", \"Action\", \"Blokker\"]")
    private List<String> sentences;

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public List<String> getSentences() {
        return sentences;
    }

    public void setSentences(List<String> sentences) {
        this.sentences = sentences;
    }
}
