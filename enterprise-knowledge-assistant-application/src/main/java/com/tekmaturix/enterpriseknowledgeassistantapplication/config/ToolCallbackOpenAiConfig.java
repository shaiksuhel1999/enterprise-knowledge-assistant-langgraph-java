package com.tekmaturix.enterpriseknowledgeassistantapplication.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tekmaturix.enterpriseknowledgeassistantapplication.service.DatabaseEmployeeService;

@Configuration
public class ToolCallbackOpenAiConfig {

	public ToolCallbackProvider toolCallbackProviderLocal(DatabaseEmployeeService databaseEmployeeService) {
		return MethodToolCallbackProvider.builder().toolObjects(databaseEmployeeService).build();
	}

	@Bean
	public ChatClient localToolChatClient(ChatClient.Builder builder, DatabaseEmployeeService databaseEmployeeService) {
		ChatMemory memory = MessageWindowChatMemory.builder().chatMemoryRepository(new InMemoryChatMemoryRepository())
				.maxMessages(100000).build();
		return builder.defaultAdvisors(MessageChatMemoryAdvisor.builder(memory).build()).defaultSystem("""
				you're an intelligent Enterprise Knowledge Assistant
				""").defaultTools(toolCallbackProviderLocal(databaseEmployeeService)).build();
	}

}
