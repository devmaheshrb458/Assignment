package com.assignment.facts.repository;

import com.assignment.facts.dto.Facts;
import com.assignment.facts.dto.FactsResponse;
import com.assignment.network.ApiListener;


/**
 * @author Mahesh R Bhatkande (mahesh.bhatkande@infosys.com)
 * @since 14 Apr, 2018
 */

public class FactsRepository {

    private FactsRemoteRepository factsRemoteRepository;

    public FactsRepository(){
        this.factsRemoteRepository = new FactsRemoteRepository();
    }

    /**
     * Load facts from remote.
     *
     * @param apiListener HTTP call listener
     */
    public void loadFactsFromRemote(ApiListener<FactsResponse> apiListener){
        this.factsRemoteRepository.loadFacts(apiListener);
    }
}
