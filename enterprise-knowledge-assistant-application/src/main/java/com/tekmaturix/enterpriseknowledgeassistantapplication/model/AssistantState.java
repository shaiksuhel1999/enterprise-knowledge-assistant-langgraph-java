package com.tekmaturix.enterpriseknowledgeassistantapplication.model;

import static java.util.Map.entry;

import java.util.Map;
import java.util.Objects;

import org.bsc.langgraph4j.state.AgentState;
import org.bsc.langgraph4j.state.Channel;
import org.bsc.langgraph4j.state.Channels;

public class AssistantState extends AgentState {

	public AssistantState(Map<String, Object> initData) {
		super(initData);
	}

	public static final String QUERY = "query";
	public static final String QUERY_TYPE = "queryType";
	public static final String TOOL_RESULT = "toolresult";
	public static final String ANSWER = "answer";

	public static final Map<String, Channel<?>> SCHEMA = Map.<String, Channel<?>>ofEntries(
			entry(QUERY, Channels.base(() -> "")), entry(QUERY_TYPE, Channels.base(() -> "")),
			entry(TOOL_RESULT, Channels.base(() -> "")), entry(ANSWER, Channels.base(() -> "")));
	
	public String getQuery() {
		return value(QUERY).map(Objects::toString).orElse("");
	}
	
	public String getQueryType() {
		return value(QUERY_TYPE).map(Objects::toString).orElse("");
	}
	
	public String getToolResult() {
		return value(TOOL_RESULT).map(Objects::toString).orElse("");
	}

	public String getAnswer() {
		return value(ANSWER).map(Objects::toString).orElse("");
	}

}
