package com.tekmaturix.enterpriseknowledgeassistantapplication.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

@Service
public class VectorStoreEmployeePolicyService {

	// Configuration constants for better search quality
	private static final int TOP_K_RESULTS = 1;
	private static final double SIMILARITY_THRESHOLD = 0.5; // Only return results with score > 50%

	private final VectorStore vectorStore;

	public VectorStoreEmployeePolicyService(VectorStore vectorStore) {
		super();
		this.vectorStore = vectorStore;
	}

	public List<Document> getPolicy(String information) throws Exception {
		List<Document> results = vectorStore.similaritySearch(

				SearchRequest.builder().query(information).topK(TOP_K_RESULTS).build());
		return results.stream().filter(doc -> {
			// Extract similarity score from metadata
			Object scoreObj = doc.getMetadata().get("score");
			if (scoreObj instanceof Number) {
				double score = ((Number) scoreObj).doubleValue();
				return score >= SIMILARITY_THRESHOLD;
			}
			return true; // If no score, include it
		}).sorted((doc1, doc2) -> {
			// Sort by score descending (highest relevance first)
			Double score1 = extractScore(doc1);
			Double score2 = extractScore(doc2);
			return score2.compareTo(score1);
		}).collect(Collectors.toList());
	}

	/**
	 * Extract similarity score from document metadata
	 */
	private Double extractScore(Document doc) {
		Object scoreObj = doc.getMetadata().get("score");
		if (scoreObj instanceof Number) {
			return ((Number) scoreObj).doubleValue();
		}
		return 0.0;
	}

}
