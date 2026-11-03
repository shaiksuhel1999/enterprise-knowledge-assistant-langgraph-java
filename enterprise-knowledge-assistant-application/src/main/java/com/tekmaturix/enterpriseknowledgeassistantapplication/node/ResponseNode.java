package com.tekmaturix.enterpriseknowledgeassistantapplication.node;

import java.util.Map;

import org.bsc.langgraph4j.action.NodeAction;
import org.springframework.ai.chat.client.ChatClient;

import com.tekmaturix.enterpriseknowledgeassistantapplication.model.AssistantState;

public class ResponseNode implements NodeAction<AssistantState>{
	
	private final ChatClient chatClient;
	
	public ResponseNode(ChatClient chatClient) {
		super();
		this.chatClient = chatClient;
	}

	@Override
	public Map<String, Object> apply(AssistantState state) throws Exception {
		 String answer = chatClient.prompt()
	                .system("""
	                    Answer the user's question clearly and honestly.
	                    Use the supplied execution result as context.
	                    If the result is only a placeholder, explain that the
	                    relevant integration is not implemented yet.
	                    Never claim that a database or external tool was queried
	                    unless the result confirms that it was.
	                    """)
	                .user("""
	                    User question:
	                    %s

	                    Classification:
	                    %s

	                    Execution result:
	                    %s
	                    """.formatted(
	                        state.getQuery(),
	                        state.getQueryType(),
	                        state.getToolResult()
	                    ))
	                .call()
	                .content();
		 return Map.of(
				 AssistantState.ANSWER, 
				 answer
				 );
	}

}
