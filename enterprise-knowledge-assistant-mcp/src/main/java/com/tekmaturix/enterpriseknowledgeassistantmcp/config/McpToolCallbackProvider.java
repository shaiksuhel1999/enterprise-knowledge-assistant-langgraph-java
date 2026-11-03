package com.tekmaturix.enterpriseknowledgeassistantmcp.config;

import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tekmaturix.enterpriseknowledgeassistantmcp.service.EnterPriseKnowledgeAssistantToolService;

@Configuration
public class McpToolCallbackProvider {
	
	@Bean
	public ToolCallbackProvider toolCallbackProviderRemote(EnterPriseKnowledgeAssistantToolService enterPriseKnowledgeAssistantToolService) {
		return MethodToolCallbackProvider.builder()
				.toolObjects(enterPriseKnowledgeAssistantToolService)
				.build();
	}

}
