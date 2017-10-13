package com.sixelasavir.prueba.entrevista.retrofit.model.app;

import com.google.gson.annotations.SerializedName;

/**
 * Created by arivas on 11/10/2017.
 */

public class Response {

    private String kind;

    @SerializedName("data")
    private DataListing dataListing;

    public Response() {
    }

    public Response(String kind, DataListing dataListing) {
        this.kind = kind;
        this.dataListing = dataListing;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public DataListing getDataListing() {
        return dataListing;
    }

    public void setDataListing(DataListing dataListing) {
        this.dataListing = dataListing;
    }
}
