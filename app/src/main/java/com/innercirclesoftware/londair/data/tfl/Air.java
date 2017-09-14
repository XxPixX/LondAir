package com.innercirclesoftware.londair.data.tfl;


import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Air {

    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("$type")
    @Expose
    private String $type;
    @SerializedName("updatePeriod")
    @Expose
    private String updatePeriod;
    @SerializedName("updateFrequency")
    @Expose
    private String updateFrequency;
    @SerializedName("forecastURL")
    @Expose
    private String forecastURL;
    @SerializedName("disclaimerText")
    @Expose
    private String disclaimerText;
    @SerializedName("currentForecast")
    @Expose
    private List<CurrentForecast> currentForecast = null;

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

    public String getUpdatePeriod() {
        return updatePeriod;
    }

    public void setUpdatePeriod(String updatePeriod) {
        this.updatePeriod = updatePeriod;
    }

    public String getUpdateFrequency() {
        return updateFrequency;
    }

    public void setUpdateFrequency(String updateFrequency) {
        this.updateFrequency = updateFrequency;
    }

    public String getForecastURL() {
        return forecastURL;
    }

    public void setForecastURL(String forecastURL) {
        this.forecastURL = forecastURL;
    }

    public String getDisclaimerText() {
        return disclaimerText;
    }

    public void setDisclaimerText(String disclaimerText) {
        this.disclaimerText = disclaimerText;
    }

    public List<CurrentForecast> getCurrentForecast() {
        return currentForecast;
    }

    public void setCurrentForecast(List<CurrentForecast> currentForecast) {
        this.currentForecast = currentForecast;
    }

    @Override
    public String toString() {
        return "Air{" +
                "$id='" + $id + '\'' +
                ", $type='" + $type + '\'' +
                ", updatePeriod='" + updatePeriod + '\'' +
                ", updateFrequency='" + updateFrequency + '\'' +
                ", forecastURL='" + forecastURL + '\'' +
                ", disclaimerText='" + disclaimerText + '\'' +
                ", currentForecast=" + currentForecast +
                '}';
    }
}
