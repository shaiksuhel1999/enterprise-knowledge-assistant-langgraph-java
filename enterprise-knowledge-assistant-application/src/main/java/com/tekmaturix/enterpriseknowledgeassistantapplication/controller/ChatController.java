package com.tekmaturix.enterpriseknowledgeassistantapplication.controller;

import java.util.Map;

import org.bsc.langgraph4j.CompiledGraph;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.tekmaturix.enterpriseknowledgeassistantapplication.dto.ChatRequest;
import com.tekmaturix.enterpriseknowledgeassistantapplication.dto.ChatResponse;
import com.tekmaturix.enterpriseknowledgeassistantapplication.model.AssistantState;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
    private final CompiledGraph<AssistantState> graph;
    public ChatController(CompiledGraph<AssistantState> graph) { this.graph = graph; }

    @PostMapping
    public ChatResponse chat(@RequestBody ChatRequest request) throws Exception {
        if (request == null || request.question() == null || request.question().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "question must not be blank");
        }
        var result = graph.invoke(Map.of(AssistantState.QUERY, request.question().trim()));
        AssistantState state = result.orElseThrow(() -> new IllegalStateException("Graph returned no state"));
        return new ChatResponse(state.getQuery(), state.getQueryType(), state.getAnswer());
    }
}
