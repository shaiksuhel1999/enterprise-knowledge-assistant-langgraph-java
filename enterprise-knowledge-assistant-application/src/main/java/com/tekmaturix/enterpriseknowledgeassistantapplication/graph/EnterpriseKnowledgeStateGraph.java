package com.tekmaturix.enterpriseknowledgeassistantapplication.graph;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.bsc.langgraph4j.CompiledGraph;
import org.bsc.langgraph4j.StateGraph;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Qualifier;

import com.tekmaturix.enterpriseknowledgeassistantapplication.model.AssistantState;
import com.tekmaturix.enterpriseknowledgeassistantapplication.node.ClassificationNode;
import com.tekmaturix.enterpriseknowledgeassistantapplication.node.DatabaseNode;
import com.tekmaturix.enterpriseknowledgeassistantapplication.node.GenericNode;
import com.tekmaturix.enterpriseknowledgeassistantapplication.node.McpNode;
import com.tekmaturix.enterpriseknowledgeassistantapplication.node.MultiSourceNode;
import com.tekmaturix.enterpriseknowledgeassistantapplication.node.RagNode;
import com.tekmaturix.enterpriseknowledgeassistantapplication.node.ResponseNode;
import com.tekmaturix.enterpriseknowledgeassistantapplication.service.VectorStoreEmployeePolicyService;

import static org.bsc.langgraph4j.GraphDefinition.END;
import static org.bsc.langgraph4j.GraphDefinition.START;
import static org.bsc.langgraph4j.action.AsyncNodeAction.node_async;

@Configuration
public class EnterpriseKnowledgeStateGraph {

    @Bean
    public CompiledGraph<AssistantState> knowledgeAssistantGraph(
            @Qualifier("chatClient") ChatClient chatClient,
            @Qualifier("localToolChatClient") ChatClient localToolChatClient,
            @Qualifier("remoteToolChatClient") ChatClient remoteToolChatClient,
            VectorStoreEmployeePolicyService policyService) throws Exception {

        var graph = new StateGraph<>(AssistantState.SCHEMA, AssistantState::new)
            .addNode("classification", node_async(new ClassificationNode(chatClient)))
            .addNode("database", node_async(new DatabaseNode(localToolChatClient)))
            .addNode("rag", node_async(new RagNode(chatClient, policyService)))
            .addNode("mcp", node_async(new McpNode(remoteToolChatClient)))
            .addNode("general", node_async(new GenericNode(chatClient)))
            .addNode("multi_source", node_async(new MultiSourceNode(chatClient, localToolChatClient, policyService)))
            .addNode("response", node_async(new ResponseNode(chatClient)))
            .addEdge(START, "classification")
            .addConditionalEdges("classification", state -> CompletableFuture.completedFuture(
                switch (state.getQueryType()) {
                    case "DATABASE" -> "database";
                    case "RAG" -> "rag";
                    case "MCP" -> "mcp";
                    case "MULTI_SOURCE" -> "multi_source";
                    default -> "general";
                }), Map.of("database", "database", "rag", "rag", "mcp", "mcp",
                    "multi_source", "multi_source", "general", "general"))
            .addEdge("database", "response")
            .addEdge("rag", "response")
            .addEdge("mcp", "response")
            .addEdge("multi_source", "response")
            .addEdge("general", "response")
            .addEdge("response", END);

        return graph.compile();
    }
}
