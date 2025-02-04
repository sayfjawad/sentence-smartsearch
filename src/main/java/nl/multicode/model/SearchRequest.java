package nl.multicode.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(description = "Request object for Smart Sentence Search")
public class SearchRequest {

    @Schema(description = "Search term to find", example = "Albert Heijn", required = true)
    public String searchTerm;

    @Schema(description = "Large text where the search is performed",
            example = "We went to the Albert Heijn store, and got some Aubergine and Heineken brew!", required = true)
    public String text;
}
