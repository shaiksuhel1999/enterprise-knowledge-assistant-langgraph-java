package com.tekmaturix.enterpriseknowledgeassistantapplication.node;

import java.util.Map;

import org.bsc.langgraph4j.action.NodeAction;
import org.springframework.ai.chat.client.ChatClient;

import com.tekmaturix.enterpriseknowledgeassistantapplication.model.AssistantState;

public class GenericNode implements NodeAction<AssistantState> {

	private final ChatClient chatClient;

	public GenericNode(ChatClient chatClient) {
		super();
		this.chatClient = chatClient;
	}

	@Override
	public Map<String, Object> apply(AssistantState state) throws Exception {
		String response = chatClient.prompt()
				.system("you're an intelligent enterprise knowledge assistant, answer the question precisely")
				.user(state.getQuery()).call().content();
		return Map.of(AssistantState.TOOL_RESULT, response);
	}

}
