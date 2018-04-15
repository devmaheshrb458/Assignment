package com.assignment.facts;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.assignment.R;
import com.assignment.facts.dto.Fact;
import com.assignment.utils.Validation;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

/**
 * Adapter that provides views to display facts in {@link android.support.v7.widget.RecyclerView}
 *
 * @author Mahesh R Bhatkande (mahesh.bhatkande@infosys.com)
 * @since 14 Apr, 2018
 */

public class FactsAdapter extends RecyclerView.Adapter<FactsAdapter.FactViewHolder> {

    private ArrayList<Fact> factsList;
    private LayoutInflater layoutInflater;
    private Context context;

    FactsAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    /**
     * Update facts list. Clear existing facts and add new.
     *
     * @param factsList Latest Fact List
     */
    void updateFactsList(ArrayList<Fact> factsList) {
        if (this.factsList == null)
            this.factsList = new ArrayList<>();
        else
            this.factsList.clear();

        this.factsList.addAll(factsList);
    }

    /**
     * Clear facts. This will be called when on refresh we have received no facts in response
     */
    void clearFacts() {
        if (this.factsList != null) {
            this.factsList.clear();
        }
    }

    @Override
    public FactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = this.layoutInflater.inflate(R.layout.list_items_facts_list, parent, false);
        return new FactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FactViewHolder factViewHolder, int position) {

        //get fact at position
        Fact fact = this.factsList.get(position);

        //set title
        String title = fact.getTitle();
        if (Validation.isNullOrEmpty(title))
            factViewHolder.textFactTitle.setText(R.string.not_available);
        else
            factViewHolder.textFactTitle.setText(title);

        //set description
        String description = fact.getDescription();
        if (Validation.isNullOrEmpty(description))
            factViewHolder.textFactDescription.setText(R.string.not_available);
        else
            factViewHolder.textFactDescription.setText(description);

        //Lazy load fact thumbnail
        Glide.with(context)
                .load(fact.getImageUrl())
                .placeholder(R.drawable.img_facts_list_item_place_holder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.img_facts_list_item_place_holder)
                .into(factViewHolder.imageFact);
    }


    @Override
    public int getItemCount() {
        return this.factsList == null ? 0 : this.factsList.size();
    }


    /**
     * A {@link android.support.v7.widget.RecyclerView.ViewHolder} for each fact view displayed by
     * {@link RecyclerView}
     */
    final class FactViewHolder extends RecyclerView.ViewHolder {

        TextView textFactTitle;
        TextView textFactDescription;
        ImageView imageFact;

        FactViewHolder(View itemView) {
            super(itemView);
            this.textFactTitle = itemView.findViewById(R.id.textFactTitle);
            this.textFactDescription = itemView.findViewById(R.id.textFactDescription);
            this.imageFact = itemView.findViewById(R.id.imageFact);
        }
    }
}
