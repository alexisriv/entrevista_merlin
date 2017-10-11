package com.sixelasavir.prueba.entrevista.retrofit.model.category;

import com.google.gson.annotations.SerializedName;

/**
 * Created by arivas on 11/10/2017.
 */

public class Children {

    private String kind;

    @SerializedName("data")
    private DataChildren dataChildren;

    public Children() {
    }

    public Children(String kind, DataChildren dataChildren) {
        this.kind = kind;
        this.dataChildren = dataChildren;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public DataChildren getDataChildren() {
        return dataChildren;
    }

    public void setDataChildren(DataChildren dataChildren) {
        this.dataChildren = dataChildren;
    }
}
