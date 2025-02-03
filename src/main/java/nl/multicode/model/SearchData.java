package nl.multicode.model;

import lombok.Data;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.List;

@Data
@Schema(description = "Data structure for search operation")
public class SearchData {

    @Schema(description = "Search term to look for", example = "Albert Heijn")
    private String search;

    @Schema(description = "List of sentences to search in", example = "[\"Albert Heijn store\", \"Action\", \"Blokker\"]")
    private List<String> sentences;
}
