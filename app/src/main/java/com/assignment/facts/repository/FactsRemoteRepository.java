package com.assignment.facts.repository;

import android.support.annotation.NonNull;

import com.assignment.facts.FactsService;
import com.assignment.facts.dto.Fact;
import com.assignment.facts.dto.FactsResponse;
import com.assignment.network.ApiListener;
import com.assignment.network.ServiceGenerator;
import com.assignment.utils.Validation;

import java.util.ArrayList;

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
    void getFacts(final ApiListener<FactsResponse> apiListener) {

        FactsService factsService = ServiceGenerator.createService(FactsService.class);
        Call<FactsResponse> factsCall = factsService.fetchFacts();
        factsCall.enqueue(new Callback<FactsResponse>() {
            @Override
            public void onResponse(@NonNull Call<FactsResponse> call, @NonNull Response<FactsResponse> response) {
                FactsResponse factsResponse = formatResponse(response.body());
                if (factsResponse != null) {
                    apiListener.onSuccess(factsResponse);
                } else {
                    apiListener.onFailure(new Exception());
                }
            }

            @Override
            public void onFailure(@NonNull Call<FactsResponse> call, @NonNull Throwable t) {
                apiListener.onFailure(t);
            }
        });
    }

    /**
     * Called when user refresh facts by swiping down.
     *
     * @param apiListener HTTP call listener
     */
    void refreshFacts(final ApiListener<FactsResponse> apiListener) {
        getFacts(apiListener);
    }

    /**
     * Remove facts which don't have title, description and image urls
     *
     * @param response API response body
     */
    private FactsResponse formatResponse(FactsResponse response) {

        //check if the response is null
        if (response == null || response.getFactsList() == null) {
            return null;
        }

        //Remove facts which has no value
        ArrayList<Fact> factsList = response.getFactsList();
        for (int i = 0; i < factsList.size(); i++) {
            Fact fact = factsList.get(i);
            if (Validation.isNullOrEmpty(fact.getTitle()) &&
                    Validation.isNullOrEmpty(fact.getDescription()) &&
                    Validation.isNullOrEmpty(fact.getImageUrl())) {
                factsList.remove(fact);
            }
        }

        return response;
    }

}
