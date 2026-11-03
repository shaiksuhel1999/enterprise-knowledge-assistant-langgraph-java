package com.tekmaturix.enterpriseknowledgeassistantmcp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import com.tekmaturix.enterpriseknowledgeassistantmcp.model.ClientDetail;
import com.tekmaturix.enterpriseknowledgeassistantmcp.repo.EnterPriseKnowledgeAssistantToolRepo;

@Component
public class EnterPriseKnowledgeAssistantToolService {
	
	private final EnterPriseKnowledgeAssistantToolRepo enterPriseKnowledgeAssistantToolRepo;
	
	public EnterPriseKnowledgeAssistantToolService(EnterPriseKnowledgeAssistantToolRepo enterPriseKnowledgeAssistantToolRepo) {
		super();
		this.enterPriseKnowledgeAssistantToolRepo = enterPriseKnowledgeAssistantToolRepo;
	}
	
	@Tool(
			name = "getAllClientsInformation",
			description = "Returns the full details of every client (name, value, years, type)."
			)
	public List<ClientDetail> clients() {
		return enterPriseKnowledgeAssistantToolRepo.findAll();
	}
	
	@Tool(
			name = "getClientInformation", 
			description = "Returns the full infomation of client (name, value, years, type)."
			)
	public ClientDetail client(String name) {
		return enterPriseKnowledgeAssistantToolRepo.findByClientName(name).orElseThrow();
	}
	
	@Tool(
			name = "getAllClientNames", 
			description = "This function loads up all the client names"
			)
	public List<String> clientsList() {
		return enterPriseKnowledgeAssistantToolRepo.findAll().stream().map(clinet-> clinet.getClientName()).collect(Collectors.toList());
	}
	
	
	@Tool(
			name = "getUSAClientNames",
			description = "Returns the names of all USA-based clients."
			)
	public List<String> usClientsList() {
		return enterPriseKnowledgeAssistantToolRepo.findAll().parallelStream().filter(client-> client.getClientType().equalsIgnoreCase("usa"))
				.map(client->client.getClientName()).collect(Collectors.toList());
	}
	
	@Tool(
			name = "getLocalClientNames",
			description = "Returns the names of all non-USA (local) clients."
			)
	public List<String> localClientsList() {
		return enterPriseKnowledgeAssistantToolRepo.findAll().parallelStream().filter(client-> !client.getClientType().equalsIgnoreCase("usa"))
				.map(client->client.getClientName()).collect(Collectors.toList());
	}
	
	@Tool(
			name = "getOldestClient",
			description = "Returns the name of the oldest client (most years with the organization)."
			)
	public String oldestClient() throws Exception {
		return enterPriseKnowledgeAssistantToolRepo.findFirstByOrderByNoyDesc().orElseThrow(()-> new Exception("Nothing presents")).getClientName();
	}
	
	@Tool(
			name = "getNewestClient",
			description = "Returns the name of the newest client (fewest years with the organization)."
			)
	public String newestClient() throws Exception {
		return enterPriseKnowledgeAssistantToolRepo.findFirstByOrderByNoyAsc().orElseThrow(()-> new Exception("Nothing presents")).getClientName();
	}
	
	@Tool(
			name = "getMoreValuedClient",
			description = "Returns the name of the most valuable client (highest value held with the organization)."
			)
	public String moreValuedClient() throws Exception {
		return enterPriseKnowledgeAssistantToolRepo.findFirstByOrderByClientValueDesc().orElseThrow(()-> new Exception("Nothing presents")).getClientName();
	}
	
	@Tool(
			name = "getLeastValuedClient",
			description = "Returns the name of the least valuable client (lowest value held with the organization)."
			)
	public String leastValuedClinet()  throws Exception {
		return enterPriseKnowledgeAssistantToolRepo.findFirstByOrderByClientValueAsc().orElseThrow(()-> new Exception("Nothing presents")).getClientName();
	}

}
