package com.sixelasavir.prueba.entrevista.retrofit.model.app;

import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;

/**
 * Created by arivas on 11/10/2017.
 */

public class DataChildren {

    private String title;

    private String thumbnail;

    private Bitmap thumbnailBitmap;

    private String permalink;

    private String url;

    private String author;

    public DataChildren() {
    }

    public DataChildren(String title, String thumbnail, Bitmap thumbnailBitmap, String permalink, String url, String author) {
        this.title = title;
        this.thumbnail = thumbnail;
        this.thumbnailBitmap = thumbnailBitmap;
        this.permalink = permalink;
        this.url = url;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Bitmap getThumbnailBitmap() {
        return thumbnailBitmap;
    }

    public void setThumbnailBitmap(Bitmap thumbnailBitmap) {
        this.thumbnailBitmap = thumbnailBitmap;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
