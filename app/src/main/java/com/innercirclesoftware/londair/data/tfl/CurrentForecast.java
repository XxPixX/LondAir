package com.innercirclesoftware.londair.data.tfl;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrentForecast {

    public static final String BAND_HIGH = "High";
    public static final String BAND_MODERATE = "Moderate";
    public static final String BAND_LOW = "Low";
    public static final String BAND_NONE = "None";

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

    @ForecastBand
    public String getForecastBand() {
        return forecastBand;
    }

    public String getForecastSummary() {
        return forecastSummary;
    }

    public String getNO2Band() {
        return nO2Band;
    }

    public String getO3Band() {
        return o3Band;
    }

    public String getPM10Band() {
        return pM10Band;
    }

    public String getPM25Band() {
        return pM25Band;
    }

    public String getSO2Band() {
        return sO2Band;
    }

    public String getForecastText() {
        return forecastText;
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
}