package com.assignment.facts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.assignment.R;

/**
 * Activity that will host Fact list Fragment {@link FactsListFragment}.
 *
 * @author Mahesh R Bhatkande (mahesh.bhatkande@infosys.com)
 * @since 13 Apr, 2018
 */

public class FactsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facts);

        addFactsListFragment();
    }

    /**
     * Add Fact list fragment {@link FactsListFragment}
     */
    private void addFactsListFragment(){
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragmentContainer, new FactsListFragment())
                .commit();
    }
}
