package com.tekmaturix.enterpriseknowledgeassistantapplication.node;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bsc.langgraph4j.action.NodeAction;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;

import com.tekmaturix.enterpriseknowledgeassistantapplication.model.AssistantState;
import com.tekmaturix.enterpriseknowledgeassistantapplication.service.VectorStoreEmployeePolicyService;

public class MultiSourceNode implements NodeAction<AssistantState> {
    private final ChatClient chatClient;
    private final ChatClient databaseChatClient;
    private final VectorStoreEmployeePolicyService policyService;

    public MultiSourceNode(ChatClient chatClient, ChatClient databaseChatClient, VectorStoreEmployeePolicyService policyService) {
        this.chatClient = chatClient; this.databaseChatClient = databaseChatClient; this.policyService = policyService;
    }

    @Override
    public Map<String, Object> apply(AssistantState state) throws Exception {
        List<Document> docs = policyService.getPolicy(state.getQuery());
        String policyContext = docs.stream().map(Document::getText).collect(Collectors.joining("\n---\n"));
        String dbContext = databaseChatClient.prompt()
            .system("Answer using the connected employee database tools. If data is unavailable, say so. Return only the result.")
            .user(state.getQuery()).call().content();
        String answer = chatClient.prompt()
            .system("Answer using only the supplied database result and policy excerpts. Clearly identify missing evidence.")
            .user("Question: " + state.getQuery() + "\nDatabase result: " + dbContext + "\nPolicy excerpts: " + policyContext)
            .call().content();
        return Map.of(AssistantState.TOOL_RESULT, answer == null ? "" : answer);
    }
}
