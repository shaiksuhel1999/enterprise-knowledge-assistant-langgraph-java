package com.tekmaturix.enterpriseknowledgeassistantapplication.node;

import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.bsc.langgraph4j.action.NodeAction;
import org.springframework.ai.chat.client.ChatClient;

import com.tekmaturix.enterpriseknowledgeassistantapplication.model.AssistantState;

public class ClassificationNode implements NodeAction<AssistantState> {
    private static final Set<String> ROUTES = Set.of(
        "DATABASE", "RAG", "MCP", "MULTI_SOURCE", "GENERAL");
    private final ChatClient chatClient;

    public ClassificationNode(ChatClient chatClient) { this.chatClient = chatClient; }

    @Override
    public Map<String, Object> apply(AssistantState state) {
        String raw = chatClient.prompt()
            .system("""
                Classify the user question into exactly one route: DATABASE, RAG, MCP, MULTI_SOURCE, GENERAL.
                DATABASE means structured employee/business records.
                RAG means company policy/document knowledge.
                MCP means a connected external MCP tool/API.
                MULTI_SOURCE means at least two sources are required.
                GENERAL means greeting or general question.
                Return only the route label, no punctuation or explanation.
                """)
            .user(state.getQuery()).call().content();
        String route = raw == null ? "GENERAL" : raw.trim().toUpperCase(Locale.ROOT);
        if (!ROUTES.contains(route)) route = "GENERAL";
        return Map.of(AssistantState.QUERY_TYPE, route);
    }
}
