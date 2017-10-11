package com.sixelasavir.prueba.entrevista.retrofit.model.category;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by arivas on 11/10/2017.
 */

public class DataListing {

    private String modhash;

    @SerializedName("whitelist_status")
    private String whitelistStatus;
    private List<Children> children;
    private String after;
    private String before;

    public DataListing() {
    }

    public DataListing(String modhash, String whitelist_status, List children, String after, String before) {
        this.modhash = modhash;
        this.whitelistStatus = whitelist_status;
        this.children = children;
        this.after = after;
        this.before = before;
    }

    public String getModhash() {
        return modhash;
    }

    public void setModhash(String modhash) {
        this.modhash = modhash;
    }

    public String getWhitelistStatus() {
        return whitelistStatus;
    }

    public void setWhitelistStatus(String whitelist_status) {
        this.whitelistStatus = whitelist_status;
    }

    public List getChildren() {
        return children;
    }

    public void setChildren(List children) {
        this.children = children;
    }

    public String getAfter() {
        return after;
    }

    public void setAfter(String after) {
        this.after = after;
    }

    public String getBefore() {
        return before;
    }

    public void setBefore(String before) {
        this.before = before;
    }
}
