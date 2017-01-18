package com.develogical;

import com.weather.Forecast;
import com.weather.Forecaster;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;
import com.weather.Region;
import com.weather.Day;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class WeatherProxyTests {

    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();

    @Test
    public void getWeatherTest() {
        WeatherCache cache = new WeatherCache();
        IWeatherForecaster forecaster = new WeatherForecaster();

        WeatherProxy proxy = new WeatherProxy(cache, forecaster);

        String cacheKey = Region.LONDON.toString() + "_" + Day.MONDAY.toString();

        proxy.getWeatherByRegion(Region.LONDON, Day.MONDAY);

        boolean hasKey = cache.hasItem(cacheKey);

        assertThat(hasKey,equalTo(true));
    }

    @Test
    public void cacheHasItem() {
        WeatherCache cache = new WeatherCache();

        Forecast forecast = new Forecast("London",1);
        cache.addItem("123",forecast);

        boolean hasItem = cache.hasItem("123");

        assertThat(hasItem, equalTo(true));
    }

    @Test
    public void addAndGetItemTest(){
        WeatherCache cache = new WeatherCache();
        Forecast forecast = new Forecast("London",1);

        cache.addItem("123",forecast);

        Forecast expected = cache.getItem("123");

        assertThat(expected.summary(),equalTo("London"));
        assertThat(expected.temperature(),equalTo(1));
    }

    @Test
    public void cacheSizeTest(){
        WeatherCache cache = new WeatherCache();

        cache.addItem("121",new Forecast("London",1));
        cache.addItem("122",new Forecast("London",2));
        cache.addItem("123",new Forecast("London",3));
        cache.addItem("124",new Forecast("London",4));

        int size = cache.size();
        assertThat(size,equalTo(3));
        assertThat(cache.hasItem("121"),equalTo(false));
        assertThat(cache.hasItem("124"),equalTo(true));
    }


    @Test
    public void verifyIfFromCache(){
        final IWeatherForecaster forecaster = context.mock(IWeatherForecaster.class);

        context.checking(new Expectations(){{
            exactly(1).of(forecaster).forecastFor(Region.LONDON, Day.MONDAY);
        }});

        WeatherCache cache = new WeatherCache();
        WeatherProxy proxy = new WeatherProxy(cache, forecaster);

        proxy.getWeatherByRegion(Region.LONDON, Day.MONDAY);
        proxy.getWeatherByRegion(Region.LONDON, Day.MONDAY);

    }

}
