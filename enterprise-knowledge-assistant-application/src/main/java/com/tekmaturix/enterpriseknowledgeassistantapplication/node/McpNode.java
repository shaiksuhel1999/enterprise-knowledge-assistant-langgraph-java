package com.tekmaturix.enterpriseknowledgeassistantapplication.node;

import java.util.Map;

import org.bsc.langgraph4j.action.NodeAction;
import org.springframework.ai.chat.client.ChatClient;

import com.tekmaturix.enterpriseknowledgeassistantapplication.model.AssistantState;

public class McpNode implements NodeAction<AssistantState> {

	private final ChatClient remoteToolChatClient;

	public McpNode(ChatClient remoteToolChatClient) {
		super();
		this.remoteToolChatClient = remoteToolChatClient;
	}

	@Override
	public Map<String, Object> apply(AssistantState state) throws Exception {

		String answer = remoteToolChatClient.prompt()
				.system("you're an intelligent system and you answer the questions based ont the attached tools")
				.user(state.getQuery()).call().content();

		return Map.of(AssistantState.TOOL_RESULT, answer);
	}

}
