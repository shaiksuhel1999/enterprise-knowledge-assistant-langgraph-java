package com.tekmaturix.enterpriseknowledgeassistantapplication.dto;

public record ChatResponse(
		String question, 
		String classification, 
		String answer
		) {

}
