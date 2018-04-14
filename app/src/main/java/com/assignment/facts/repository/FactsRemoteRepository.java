package com.assignment.facts.repository;

import com.assignment.facts.FactsService;
import com.assignment.facts.dto.Facts;
import com.assignment.facts.dto.FactsResponse;
import com.assignment.network.ApiListener;
import com.assignment.network.ServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * @author Mahesh R Bhatkande (mahesh.bhatkande@infosys.com)
 * @since 14 Apr, 2018
 */
class FactsRemoteRepository {

    /**
     * Load facts from server
     *
     * @param apiListener HTTP call listener
     */
    void loadFacts(final ApiListener<FactsResponse> apiListener){

        FactsService factsService = ServiceGenerator.createService(FactsService.class);
        Call<FactsResponse> factsCall = factsService.fetchFacts();
        factsCall.enqueue(new Callback<FactsResponse>() {
            @Override
            public void onResponse(Call<FactsResponse> call, Response<FactsResponse> response) {
                    //TODO format Response if required
                    apiListener.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<FactsResponse> call, Throwable t) {
                    apiListener.onFailure(t);
            }
        });
    }
}
