package com.tekmaturix.enterpriseknowledgeassistantmcp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="client_details")
public class ClientDetail {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "client_name")
	private String clientName;
	
	@Column(name = "client_value")
	private int clientValue;
	
	@Column(name = "no_of_years")
	private int noy;
	
	@Column(name = "client_type")
	private String clientType;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public int getClientValue() {
		return clientValue;
	}

	public void setClientValue(int clientValue) {
		this.clientValue = clientValue;
	}

	public int getNoy() {
		return noy;
	}

	public void setNoy(int noy) {
		this.noy = noy;
	}

	public String getClientType() {
		return clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

	public ClientDetail(int id, String clientName, int clientValue, int noy, String clientType) {
		super();
		this.id = id;
		this.clientName = clientName;
		this.clientValue = clientValue;
		this.noy = noy;
		this.clientType = clientType;
	}
	
	public ClientDetail() {
		super();
	}

}
