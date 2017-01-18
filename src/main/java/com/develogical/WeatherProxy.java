package com.develogical;

import com.weather.Forecast;
import com.weather.Region;
import com.weather.Day;

public class WeatherProxy implements IWeatherProxy{
    WeatherCache cache;
    IWeatherForecaster forecaster;

    public WeatherProxy(WeatherCache cache, IWeatherForecaster forecaster) {
        this.cache = cache;
        this.forecaster = forecaster;
    }

    public Forecast getWeatherByRegion(Region region, Day day) {
        String cacheKey = region.toString() + "_" + day.toString();

        Forecast londonForecast = null;

        if (!cache.hasItem(cacheKey)) {
            londonForecast = getForecast(region, day);

            cache.addItem(cacheKey, londonForecast);
        } else {
            londonForecast = cache.getItem(cacheKey);
        }

        return londonForecast;
    }

    public Forecast getForecast(Region region, Day day) {
        return forecaster.forecastFor(region, day);
    }


}
