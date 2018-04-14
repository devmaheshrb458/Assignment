package com.assignment.facts.dto;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 *
 * @author Mahesh R Bhatkande (mahesh.bhatkande@infosys.com)
 * @since 14 Apr, 2018
 */
public class FactsResponse {

    @SerializedName("title")
    private String title;

    @SerializedName("rows")
    private ArrayList<Facts> factsList;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Facts> getFactsList() {
        return factsList;
    }

    public void setFactsList(ArrayList<Facts> factsList) {
        this.factsList = factsList;
    }
}
