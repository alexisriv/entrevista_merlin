package com.sixelasavir.prueba.entrevista.retrofit.model.category;

import com.google.gson.annotations.SerializedName;

/**
 * Created by arivas on 11/10/2017.
 */

public class DataChildren {

    @SerializedName("banner_img")
    private String bannerImg;

    @SerializedName("display_name_prefixed")
    private String displayNamePrefixed;

    @SerializedName("display_name")
    private String displayName;

    @SerializedName("header_img")
    private String headerImg;

    private String title;

    @SerializedName("audience_target")
    private String audienceTarget;

    @SerializedName("icon_img")
    private String iconImg;

    @SerializedName("header_title")
    private String headerTitle;

    private String url;

    @SerializedName("advertiser_category")
    private String advertiserCategory;

    @SerializedName("public_description")
    private String publicDescription;


    public DataChildren() {
    }

    public DataChildren(String bannerImg, String displayNamePrefixed, String displayName, String headerImg, String title, String audienceTarget, String iconImg, String headerTitle, String url, String advertiserCategory, String publicDescription) {
        this.bannerImg = bannerImg;
        this.displayNamePrefixed = displayNamePrefixed;
        this.displayName = displayName;
        this.headerImg = headerImg;
        this.title = title;
        this.audienceTarget = audienceTarget;
        this.iconImg = iconImg;
        this.headerTitle = headerTitle;
        this.url = url;
        this.advertiserCategory = advertiserCategory;
        this.publicDescription = publicDescription;
    }

    public String getBannerImg() {
        return bannerImg;
    }

    public void setBannerImg(String bannerImg) {
        this.bannerImg = bannerImg;
    }

    public String getDisplayNamePrefixed() {
        return displayNamePrefixed;
    }

    public void setDisplayNamePrefixed(String displayNamePrefixed) {
        this.displayNamePrefixed = displayNamePrefixed;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getHeaderImg() {
        return headerImg;
    }

    public void setHeaderImg(String headerImg) {
        this.headerImg = headerImg;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAudienceTarget() {
        return audienceTarget;
    }

    public void setAudienceTarget(String audienceTarget) {
        this.audienceTarget = audienceTarget;
    }

    public String getIconImg() {
        return iconImg;
    }

    public void setIconImg(String iconImg) {
        this.iconImg = iconImg;
    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAdvertiserCategory() {
        return advertiserCategory;
    }

    public void setAdvertiserCategory(String advertiserCategory) {
        this.advertiserCategory = advertiserCategory;
    }

    public String getPublicDescription() {
        return publicDescription;
    }

    public void setPublicDescription(String publicDescription) {
        this.publicDescription = publicDescription;
    }
}
