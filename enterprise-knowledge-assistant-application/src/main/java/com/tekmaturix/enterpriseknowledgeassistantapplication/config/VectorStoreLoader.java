package com.tekmaturix.enterpriseknowledgeassistantapplication.config;

import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader; // Use PDF Reader
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import jakarta.annotation.PostConstruct;

@Configuration
public class VectorStoreLoader {

	@Value("classpath:employee-policy.pdf")
	private Resource textFileResource;
	
	private final VectorStore vectorStore;

	public VectorStoreLoader(EmbeddingModel embeddingModel) {
		super();
		this.vectorStore = SimpleVectorStore.builder(embeddingModel).build();
	}

	@PostConstruct
	public void init() {
		// Use PagePdfDocumentReader instead of TextReader for PDFs
		PagePdfDocumentReader pdfReader = new PagePdfDocumentReader(textFileResource);
		List<Document> documents = pdfReader.get();

		TokenTextSplitter textSplitter = TokenTextSplitter.builder()
	            .withChunkSize(512)
	            .withKeepSeparator(true)
	            .build();
				
		List<Document> splitDocuments = textSplitter.apply(documents);
		vectorStore.add(splitDocuments);
	}
	
	@Bean
	public VectorStore vectorStore() {
		return this.vectorStore;
	}
}