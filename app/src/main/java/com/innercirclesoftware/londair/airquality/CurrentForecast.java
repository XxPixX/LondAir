package com.innercirclesoftware.londair.airquality;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrentForecast implements Parcelable {

    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("$type")
    @Expose
    private String $type;
    @SerializedName("forecastType")
    @Expose
    private String forecastType;
    @SerializedName("forecastID")
    @Expose
    private String forecastID;
    @SerializedName("forecastBand")
    @Expose
    private String forecastBand;
    @SerializedName("forecastSummary")
    @Expose
    private String forecastSummary;
    @SerializedName("nO2Band")
    @Expose
    private String nO2Band;
    @SerializedName("o3Band")
    @Expose
    private String o3Band;
    @SerializedName("pM10Band")
    @Expose
    private String pM10Band;
    @SerializedName("pM25Band")
    @Expose
    private String pM25Band;
    @SerializedName("sO2Band")
    @Expose
    private String sO2Band;
    @SerializedName("forecastText")
    @Expose
    private String forecastText;

    public String get$id() {
        return $id;
    }

    public void set$id(String $id) {
        this.$id = $id;
    }

    public String get$type() {
        return $type;
    }

    public void set$type(String $type) {
        this.$type = $type;
    }

    public String getForecastType() {
        return forecastType;
    }

    public void setForecastType(String forecastType) {
        this.forecastType = forecastType;
    }

    public String getForecastID() {
        return forecastID;
    }

    public void setForecastID(String forecastID) {
        this.forecastID = forecastID;
    }

    public String getForecastBand() {
        return forecastBand;
    }

    public void setForecastBand(String forecastBand) {
        this.forecastBand = forecastBand;
    }

    public String getForecastSummary() {
        return forecastSummary;
    }

    public void setForecastSummary(String forecastSummary) {
        this.forecastSummary = forecastSummary;
    }

    public String getNO2Band() {
        return nO2Band;
    }

    public void setNO2Band(String nO2Band) {
        this.nO2Band = nO2Band;
    }

    public String getO3Band() {
        return o3Band;
    }

    public void setO3Band(String o3Band) {
        this.o3Band = o3Band;
    }

    public String getPM10Band() {
        return pM10Band;
    }

    public void setPM10Band(String pM10Band) {
        this.pM10Band = pM10Band;
    }

    public String getPM25Band() {
        return pM25Band;
    }

    public void setPM25Band(String pM25Band) {
        this.pM25Band = pM25Band;
    }

    public String getSO2Band() {
        return sO2Band;
    }

    public void setSO2Band(String sO2Band) {
        this.sO2Band = sO2Band;
    }

    public String getForecastText() {
        return forecastText;
    }

    public void setForecastText(String forecastText) {
        this.forecastText = forecastText;
    }

    @Override
    public String toString() {
        return "CurrentForecast{" +
                "$id='" + $id + '\'' +
                ", $type='" + $type + '\'' +
                ", forecastType='" + forecastType + '\'' +
                ", forecastID='" + forecastID + '\'' +
                ", forecastBand='" + forecastBand + '\'' +
                ", forecastSummary='" + forecastSummary + '\'' +
                ", nO2Band='" + nO2Band + '\'' +
                ", o3Band='" + o3Band + '\'' +
                ", pM10Band='" + pM10Band + '\'' +
                ", pM25Band='" + pM25Band + '\'' +
                ", sO2Band='" + sO2Band + '\'' +
                ", forecastText='" + forecastText + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.$id);
        dest.writeString(this.$type);
        dest.writeString(this.forecastType);
        dest.writeString(this.forecastID);
        dest.writeString(this.forecastBand);
        dest.writeString(this.forecastSummary);
        dest.writeString(this.nO2Band);
        dest.writeString(this.o3Band);
        dest.writeString(this.pM10Band);
        dest.writeString(this.pM25Band);
        dest.writeString(this.sO2Band);
        dest.writeString(this.forecastText);
    }

    private CurrentForecast(Parcel in) {
        this.$id = in.readString();
        this.$type = in.readString();
        this.forecastType = in.readString();
        this.forecastID = in.readString();
        this.forecastBand = in.readString();
        this.forecastSummary = in.readString();
        this.nO2Band = in.readString();
        this.o3Band = in.readString();
        this.pM10Band = in.readString();
        this.pM25Band = in.readString();
        this.sO2Band = in.readString();
        this.forecastText = in.readString();
    }

    public static final Parcelable.Creator<CurrentForecast> CREATOR = new Parcelable.Creator<CurrentForecast>() {
        @Override
        public CurrentForecast createFromParcel(Parcel source) {
            return new CurrentForecast(source);
        }

        @Override
        public CurrentForecast[] newArray(int size) {
            return new CurrentForecast[size];
        }
    };
}