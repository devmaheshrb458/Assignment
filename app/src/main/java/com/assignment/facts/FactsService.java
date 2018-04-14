package com.assignment.facts;

import com.assignment.facts.dto.FactsResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 *
 * @author Mahesh R Bhatkande (mahesh.bhatkande@infosys.com)
 * @since 14 Apr, 2018
 */
public interface FactsService {

    @GET("s/2iodh4vg0eortkl/facts.json")
    Call<FactsResponse> fetchFacts();
}
