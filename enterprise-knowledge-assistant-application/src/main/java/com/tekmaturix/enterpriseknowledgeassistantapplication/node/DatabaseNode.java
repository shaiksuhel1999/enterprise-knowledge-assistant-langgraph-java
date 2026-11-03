package com.tekmaturix.enterpriseknowledgeassistantapplication.node;

import java.util.Map;

import org.bsc.langgraph4j.action.NodeAction;
import org.springframework.ai.chat.client.ChatClient;

import com.tekmaturix.enterpriseknowledgeassistantapplication.model.AssistantState;

public class DatabaseNode implements NodeAction<AssistantState>{
	
	private final ChatClient localToolChatClient;
	
	public DatabaseNode(ChatClient localToolChatClient) {
		super();
		this.localToolChatClient = localToolChatClient;
	}

	@Override
	public Map<String, Object> apply(AssistantState state) throws Exception {
		String answer = localToolChatClient.prompt()
				.system("You're an Intelligent Enterprise Knowledge Assistant, answer precisely by referring internal attached tools")
				.user(state.getQuery())
				.call()
				.content();
		return Map.of(
				AssistantState.TOOL_RESULT, 
				answer
				);
	}

}
