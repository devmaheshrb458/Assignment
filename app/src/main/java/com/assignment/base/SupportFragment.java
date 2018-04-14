package com.assignment.base;

import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

/**
 * Support {@link Fragment} for all other Fragments. It will provide methods -
 * like to set {@link Toolbar} and it's title.
 *
 * @author Mahesh R Bhatkande (mahesh.bhatkande@infosys.com)
 * @since 13 Apr, 2018
 */

public class SupportFragment extends Fragment {

    /**
     * Add Toolbar to an Activity
     *
     * @param toolbarId Toolbar id
     * @param view View that contains the Toolbar. It is view of the {@link Fragment}
     */
    public void addToolBar(int toolbarId, View view){
        Toolbar toolbar = view.findViewById(toolbarId);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
    }

    /**
     *
     * Set title to toolbar
     *
     * @param title Title for {@link Toolbar}
     */
    public void setToolBarTitle(String title){
        ActionBar supportActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (supportActionBar != null)
            supportActionBar.setTitle(title);
    }
}
