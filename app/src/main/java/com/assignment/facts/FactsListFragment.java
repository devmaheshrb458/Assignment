package com.assignment.facts;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.assignment.R;
import com.assignment.base.SupportFragment;
import com.assignment.facts.dto.Facts;
import com.assignment.facts.dto.FactsResponse;
import com.assignment.facts.viewmodel.FactsViewModel;

import java.util.ArrayList;

/**
 * <p>
 * {@link android.support.v4.app.Fragment} which shows facts in list.
 * {@link android.support.v4.widget.SwipeRefreshLayout} helps to refresh facts by just pulling down.
 * </p>
 *
 * @author Mahesh R Bhatkande (mahesh.bhatkande@infosys.com)
 * @since 13 Apr, 2018
 */
public class FactsListFragment extends SupportFragment {

    //Swipe to refresh layout
    private SwipeRefreshLayout swipeRefreshFacts;
    //list of facts
    private RecyclerView recycleListFacts;
    /**
     * Shows indefinite progress while getting facts for first time
     */
    private ProgressBar progressBar;
    /**
     * Error message TextView. It is used to show messages when -
     * 1. No internet connection
     * 2. Zero Facts available
     * 3. Failure while getting/refreshing facts
     */
    private TextView textErrorMessage;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_facts_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addToolBar(R.id.toolBar, view);

        //get  view element references
        this.swipeRefreshFacts = view.findViewById(R.id.swipeRefreshFacts);
        this.recycleListFacts = view.findViewById(R.id.recycleListFacts);
        this.progressBar = view.findViewById(R.id.progressBar);
        this.textErrorMessage = view.findViewById(R.id.textErrorMessage);

        //configure recycler view
        this.recycleListFacts.setLayoutManager(new LinearLayoutManager(getActivity()));

        setViewDefaultStateOnStart();

        startObservingData();
    }

    /**
     * Ser default state of views in the layout
     */
    private void setViewDefaultStateOnStart(){
        this.swipeRefreshFacts.setEnabled(false);
        this.progressBar.setVisibility(View.GONE);
        this.textErrorMessage.setVisibility(View.GONE);
    }

    /**
     * Start observing data. We will get event when the corresponding data changed
     */
    private void startObservingData(){
        FactsViewModel factsViewModel = ViewModelProviders.of(this).get(FactsViewModel.class);
        factsViewModel.getFactsResponse().observe(this, new Observer<FactsResponse>() {
            @Override
            public void onChanged(@Nullable FactsResponse factsResponse) {

            }
        });

        factsViewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {

            }
        });

        factsViewModel.getIsError().observe(this, new Observer<Throwable>() {
            @Override
            public void onChanged(@Nullable Throwable throwable) {

            }
        });
    }

}
