package com.tekmaturix.enterpriseknowledgeassistantmcp.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tekmaturix.enterpriseknowledgeassistantmcp.model.ClientDetail;

@Repository
public interface EnterPriseKnowledgeAssistantToolRepo extends JpaRepository<ClientDetail, Integer> {

	// Equivalent to SELECT TOP 1 ... ORDER BY clientValue DESC
    Optional<ClientDetail> findFirstByOrderByClientValueDesc();

    // Equivalent to SELECT TOP 1 ... ORDER BY clientValue ASC
    Optional<ClientDetail> findFirstByOrderByClientValueAsc();

    // Equivalent to SELECT TOP 1 ... ORDER BY noy DESC
    Optional<ClientDetail> findFirstByOrderByNoyDesc();

    // Equivalent to SELECT TOP 1 ... ORDER BY noy ASC
    Optional<ClientDetail> findFirstByOrderByNoyAsc();
    
    Optional<ClientDetail> findByClientName(String clientName);
    
    
    
}
