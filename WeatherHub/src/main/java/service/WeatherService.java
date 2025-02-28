package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import context.Context;
import dao.WeatherRequestDao;
import dao.WeatherResponseDao;
import dto.WeatherAverageDto;
import dto.WeatherResponse;
import dto.WeatherStatDto;
import mapper.WeatherMapper;
import repository.WeatherRepository;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WeatherService {

    private final WeatherRepository repository;
    private final ObjectMapper objectMapper;

    public WeatherService(Context c){
        this.repository = c.weatherRepository;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    public String getWeather(String accessToken, String city) throws IOException, InterruptedException {
        var client = HttpClient.newBuilder().build();

        var uri = "http://api.weatherstack.com/current?access_key=" + accessToken + "&query=" + city;
        var request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(uri))
                .build();

        WeatherRequestDao weatherRequestDao = repository.saveWeatherRequest(city);

        var response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());


        WeatherResponse weatherResponse;
        weatherResponse = objectMapper.readValue(response.body(), WeatherResponse.class);

        WeatherResponseDao weatherResponseDao = WeatherMapper.mapToWeatherResponseDao(weatherResponse, weatherRequestDao);

        repository.saveWeatherResponse(weatherResponseDao);

        return "Country - " + weatherResponseDao.getCountry() + "\n" +
                "Location - " + weatherResponseDao.getLocation() + "\n" +
                "Temperature - " + weatherResponseDao.getTemperature() + "\n" +
                "Wind speed - " + weatherResponseDao.getWindSpeed();

    }

    public WeatherStatDto getWeatherStat(String city) throws IOException, InterruptedException {

        return WeatherMapper.mapToWeatherStatDto(repository.getLastWeatherRequest(city));

    }

    public WeatherAverageDto getWeatherAverage(String city) throws IOException, InterruptedException {

        return repository.getAverageWeather(city);

    }
}
