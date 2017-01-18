package com.develogical;

import com.weather.Day;
import com.weather.Forecast;
import com.weather.Region;

/**
 * Created by ape07 on 18/01/2017.
 */
public interface IWeatherForecaster {
    Forecast forecastFor(Region region, Day day);
}
