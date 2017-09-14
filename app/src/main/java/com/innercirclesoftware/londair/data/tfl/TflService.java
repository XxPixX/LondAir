package com.innercirclesoftware.londair.data.tfl;

import io.reactivex.Maybe;
import retrofit2.http.GET;

public interface TflService {

    String BASE_URL = "https://api.tfl.gov.uk";

    @GET("/AirQuality")
    Maybe<Air> getAirQuality();
    
}
