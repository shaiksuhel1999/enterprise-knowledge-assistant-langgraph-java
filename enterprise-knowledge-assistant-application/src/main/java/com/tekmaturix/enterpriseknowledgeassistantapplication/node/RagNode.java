package com.tekmaturix.enterpriseknowledgeassistantapplication.node;

import java.util.List;
import java.util.Map;

import org.bsc.langgraph4j.action.NodeAction;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;

import com.tekmaturix.enterpriseknowledgeassistantapplication.model.AssistantState;
import com.tekmaturix.enterpriseknowledgeassistantapplication.service.VectorStoreEmployeePolicyService;
public class RagNode implements NodeAction<AssistantState> {

	private final ChatClient chatClient;
	private final VectorStoreEmployeePolicyService vectorStoreEmployeePolicyService;

	public RagNode(ChatClient chatClient, VectorStoreEmployeePolicyService vectorStoreEmployeePolicyService) {
		super();
		this.chatClient = chatClient;
		this.vectorStoreEmployeePolicyService = vectorStoreEmployeePolicyService;
	}

	@Override
	public Map<String, Object> apply(AssistantState state) throws Exception {

		List<Document> documentList = vectorStoreEmployeePolicyService.getPolicy(state.getQuery());

		PromptTemplate template = new PromptTemplate("""
				you're an intelligent enterprise knowledge assistant , choose best answer and summarize it

				if not found in related documents then just say, this policy is missing in company wiki

				query: {query}

				Related_Docs: {documents}

				""");
		String userPrompt = template.create(Map.of("query", state.getQuery(), "documents", documentList.toString())

		).getContents();

		String answer = chatClient.prompt().system("you're an intelligent enterprise knowledge assist").user(userPrompt)
				.call().content();

		return Map.of(AssistantState.TOOL_RESULT, answer);

	}

}
