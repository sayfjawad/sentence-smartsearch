package nl.multicode.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nl.multicode.model.SearchRequest;
import nl.multicode.service.SmartSearchService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import java.util.List;
import java.util.Map;

@Path("/search")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SearchController {

    private final SmartSearchService smartSearchService;

    @Inject
    public SearchController(final SmartSearchService smartSearchService) {
        this.smartSearchService = smartSearchService;
    }

    @POST
    @Path("/analyze")
    @Operation(
            summary = "Perform smart search on a large text",
            description = "Accepts a search term and a large text body, and finds the most similar matches."
    )
    @APIResponse(
            responseCode = "200",
            description = "Successfully found matching sentences",
            content = @Content(mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = Map.class))
    )
    public Response search(
            @RequestBody(
                    description = "JSON payload containing search term and large text",
                    required = true,
                    content = @Content(schema = @Schema(implementation = SearchRequest.class))
            ) SearchRequest request) {

        Map<String, List<Map.Entry<String, Double>>> results = smartSearchService.performSearch(request.searchTerm, request.text);



        return Response.ok(Map.of(
                "searchTerm", request.searchTerm,
                "bestMatches", results
        )).build();
    }
}
