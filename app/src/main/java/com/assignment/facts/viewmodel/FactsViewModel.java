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

    private final MutableLiveData<Throwable> errorWhileGettingFacts = new MutableLiveData<>();

    private final MutableLiveData<Throwable> errorWhileRefreshingFacts = new MutableLiveData<>();

    private final MutableLiveData<Boolean> isRefreshCompleted = new MutableLiveData<>();

    public FactsViewModel() {
        this.factsRepository = new FactsRepository();
    }

    /**
     * Start getting facts. It will be called in scenarios like :
     * 1. Loading facts on start
     * 2. Error while loading facts for first time and user click on error message - like network error
     */
    public void getNewFacts() {
        this.isLoading.setValue(true);
        this.factsRepository.getFactsFromRemote(new ApiListener<FactsResponse>() {
            @Override
            public void onSuccess(FactsResponse response) {
                factsResponse.setValue(response);
                isLoading.setValue(false);
            }

            @Override
            public void onFailure(Throwable t) {
                isLoading.setValue(false);
                errorWhileGettingFacts.setValue(t);
            }
        });
    }

    /**
     * Called on swiping down to refresh facts.
     */
    public void refreshFacts() {
        this.factsRepository.refreshFactsFromRemote(new ApiListener<FactsResponse>() {
            @Override
            public void onSuccess(FactsResponse response) {
                isRefreshCompleted.setValue(true);
                factsResponse.setValue(response);
            }

            @Override
            public void onFailure(Throwable t) {
                isRefreshCompleted.setValue(true);
                errorWhileRefreshingFacts.setValue(t);
            }
        });
    }

    public MutableLiveData<FactsResponse> getFactsResponse() {
        return factsResponse;
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public MutableLiveData<Throwable> getErrorWhileGettingFacts() {
        return errorWhileGettingFacts;
    }

    public MutableLiveData<Throwable> getErrorWhileRefreshingFacts() {
        return errorWhileRefreshingFacts;
    }

    public MutableLiveData<Boolean> getIsRefreshCompleted() {
        return isRefreshCompleted;
    }

}
