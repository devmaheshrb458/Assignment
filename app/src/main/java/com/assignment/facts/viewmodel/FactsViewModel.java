package com.assignment.facts.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.assignment.facts.dto.FactsResponse;
import com.assignment.facts.repository.FactsRepository;
import com.assignment.network.ApiListener;

/**
 * @author Mahesh R Bhatkande (mahesh.bhatkande@infosys.com)
 * @since 14 Apr, 2018
 */

public class FactsViewModel extends ViewModel {

    private FactsRepository factsRepository;

    private final MutableLiveData<FactsResponse> factsResponse = new MutableLiveData<>();

    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    private final MutableLiveData<Throwable> isError = new MutableLiveData<>();

    public FactsViewModel() {
        this.factsRepository = new FactsRepository();
    }

    /**
     * Start getting facts. It will be called in scenarios like :
     * 1. Loading facts on start
     * 2. Error while loading facts for first time and user click on error message - like network error
     */
    public void getFacts() {
        loadFacts();
    }

    /**
     * Called on swipe to refresh action to refresh facts.
     */
    public void refreshFacts() {
        loadFacts();
    }

    /**
     * Called while fetching facts for first time or refreshing facts.
     */
    private void loadFacts() {
        this.factsRepository.loadFactsFromRemote(new ApiListener<FactsResponse>() {
            @Override
            public void onSuccess(FactsResponse response) {

            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    public MutableLiveData<FactsResponse> getFactsResponse() {
        return factsResponse;
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public MutableLiveData<Throwable> getIsError() {
        return isError;
    }

}
