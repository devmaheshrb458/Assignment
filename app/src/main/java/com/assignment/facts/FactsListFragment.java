package com.assignment.facts;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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
import com.assignment.facts.dto.Fact;
import com.assignment.facts.dto.FactsResponse;
import com.assignment.facts.viewmodel.FactsViewModel;
import com.assignment.utils.Connectivity;

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
    //ViewModel that holds data for view
    private FactsViewModel factsViewModel;
    /**
     * Shows any error message on swiping down action.
     */
    private Snackbar snackbar;
    /**
     * Adapter which provides view to display to {@link RecyclerView}
     */
    private FactsAdapter factsAdapter;
    /**
     * Shows indefinite progress while getting facts for first time.
     */
    private ProgressBar progressBar;
    /**
     * Error message TextView. It is used to show messages when -
     * 1. No internet connection
     * 2. Zero Fact available
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
        setToolBarTitle("");

        //get  view element references
        this.swipeRefreshFacts = view.findViewById(R.id.swipeRefreshFacts);
        this.recycleListFacts = view.findViewById(R.id.recycleListFacts);
        this.progressBar = view.findViewById(R.id.progressBar);
        this.textErrorMessage = view.findViewById(R.id.textErrorMessage);

        //configure recycler view - Set adapter with empty data
        this.recycleListFacts.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.factsAdapter = new FactsAdapter(getActivity());
        this.recycleListFacts.setAdapter(this.factsAdapter);

        //Configure swipeRefreshFacts
        this.swipeRefreshFacts.setColorSchemeResources(R.color.colorAero);
        this.swipeRefreshFacts.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFacts();
            }
        });

        //set click listener to views
        this.textErrorMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onErrorMessageClicked();
            }
        });

        setViewDefaultStateOnStart();

        startObservingData();

        getFactsOnStart();
    }

    /**
     * Ser default state of views in the layout
     */
    private void setViewDefaultStateOnStart() {
        this.swipeRefreshFacts.setEnabled(false);
        this.progressBar.setVisibility(View.GONE);
        this.textErrorMessage.setVisibility(View.GONE);
    }

    /**
     * Enable Swipe to refresh View so that user can refresh facts just by swiping down.
     */
    private void enableRefreshView() {
        this.swipeRefreshFacts.setEnabled(true);
    }

    /**
     * Start observing data. We will get event when the corresponding data changed
     */
    private void startObservingData() {
        this.factsViewModel = ViewModelProviders.of(this).get(FactsViewModel.class);
        this.factsViewModel.getFactsResponse().observe(this, new Observer<FactsResponse>() {
            @Override
            public void onChanged(@Nullable FactsResponse factsResponse) {
                onFactsReceived(factsResponse);
                enableRefreshView();
            }
        });

        this.factsViewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean isLoading) {
                if (isLoading != null && isLoading)
                    progressBar.setVisibility(View.VISIBLE);
                else
                    progressBar.setVisibility(View.GONE);
            }
        });

        this.factsViewModel.getErrorWhileGettingFacts().observe(this, new Observer<Throwable>() {
            @Override
            public void onChanged(@Nullable Throwable throwable) {
                textErrorMessage.setText(R.string.facts_error_unable_to_get);
                textErrorMessage.setVisibility(View.VISIBLE);
            }
        });

        this.factsViewModel.getErrorWhileRefreshingFacts().observe(this, new Observer<Throwable>() {
            @Override
            public void onChanged(@Nullable Throwable throwable) {
                Snackbar snackbar = prepareSnackBar(swipeRefreshFacts, R.string.facts_error_unable_to_refresh, Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
        });

        this.factsViewModel.getIsRefreshCompleted().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean isRefreshCompleted) {
                if (isRefreshCompleted != null && isRefreshCompleted) {
                    swipeRefreshFacts.setRefreshing(false);
                }
            }
        });
    }

    /**
     * If facts list already received from server then show it otherwise start loading new
     * facts.
     */
    private void getFactsOnStart() {
        getNewFacts();
    }

    /**
     * Start refreshing facts.
     */
    private void refreshFacts() {

        if (!Connectivity.isConnected(getActivity())) {

            this.swipeRefreshFacts.setRefreshing(false);
            Snackbar snackbar = prepareSnackBar(this.swipeRefreshFacts, R.string.no_internet_connection_snackBar, Snackbar.LENGTH_SHORT);
            snackbar.show();
            return;
        }

        this.factsViewModel.refreshFacts();
    }

    /**
     * Check if the internet connection is available. If yes then start loading new facts otherwise
     * show error message to user.
     */
    private void getNewFacts() {
        if (!Connectivity.isConnected(getActivity())) {

            this.textErrorMessage.setText(R.string.no_internet_connection_list);
            this.textErrorMessage.setVisibility(View.VISIBLE);
        } else {

            this.textErrorMessage.setVisibility(View.GONE);
            this.factsViewModel.getNewFacts();
        }
    }

    /**
     * Called when error message {@link TextView} clicked.
     */
    private void onErrorMessageClicked() {
        getNewFacts();
    }

    /**
     * Called when facts received from server
     *
     * @param factsResponse {@link FactsResponse}
     */
    private void onFactsReceived(FactsResponse factsResponse) {

        //set values to adapter and notfy about the change
        ArrayList<Fact> factsList = factsResponse.getFactsList();
        if (!factsList.isEmpty()) {

            this.factsAdapter.updateFactsList(factsList);
            this.factsAdapter.notifyDataSetChanged();
        } else {

            this.factsAdapter.clearFacts();
            this.factsAdapter.notifyDataSetChanged();

            //update error message
            this.textErrorMessage.setText(R.string.no_data_available);
            this.textErrorMessage.setVisibility(View.VISIBLE);
            this.textErrorMessage.setOnClickListener(null);
        }

        //update toolbar title
        setToolBarTitle(factsResponse.getTitle());
    }

    /**
     * Prepare and return {@link Snackbar}
     *
     * @param view   {@link View}
     * @param id     Message resource id
     * @param lenght For how much time snackbar need to shown
     * @return Snackbar {@link Snackbar} to show to user
     */
    private Snackbar prepareSnackBar(View view, int id, int lenght) {
        if (this.snackbar != null && this.snackbar.isShown()) {
            this.snackbar.dismiss();
        }
        this.snackbar = Snackbar.make(view, id, lenght);
        return snackbar;
    }

}
