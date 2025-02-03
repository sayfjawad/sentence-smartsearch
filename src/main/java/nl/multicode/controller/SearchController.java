package nl.multicode.controller;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Path("/search")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SearchController {

    @POST
    @Path("/query")
    @Operation(
            summary = "Perform smart sentence search",
            description = "Accepts a search term and a list of sentences via query parameters"
    )
    @APIResponse(
            responseCode = "200",
            description = "Successfully found matching sentences",
            content = @Content(mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = Map.class))
    )
    public Map<String, String> searchWithQueryParams(
            @Parameter(description = "Search term", example = "Albert Heijn")
            @QueryParam("search") String search,

            @Parameter(description = "Comma-separated list of sentences", example = "Albert Heijn store, Action, Blokker")
            @QueryParam("sentences") String sentences) {

        // Convert comma-separated string into a list
        List<String> sentenceList = Arrays.asList(sentences.split(","));

        // Simulating a response
        return Map.of("message", "Search executed for: " + search, "sentences", sentenceList.toString());
    }
}
