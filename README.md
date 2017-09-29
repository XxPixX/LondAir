# Londair

Londair is an android application which displays the air quality using the [TFL AirQuality REST API](https://api.tfl.gov.uk/swagger/ui/index.html?url=/swagger/docs/v1#!/AirQuality/AirQuality_Get) for displaying the air quality in London.

### Features:
* Display air quality summary and details for today and tomorrow
* Receive a daily notification when the air quality for the day crosses a threshold (low/medium/high)
* Detailed information on the source of pollutants
* Advice for people who are most affected by pollution

### Notable libraries used:
*For a full and up to date list of libraries used, check the app's [build.gradle](https://github.com/maciej-kaznowski/Londair/blob/master/app/build.gradle)*
* Dagger 2.11
* Retrofit 2
* RxJava 2
* JUnit

### Architecture:
* Clean architecture
* MVP
