package service;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dto.WeatherAverageDto;
import dto.WeatherStatDto;

public class WeatherClientService {

    private final ObjectMapper objectMapper;

    public WeatherClientService() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    public String getStatWeather(String city) throws IOException, InterruptedException {
        var client = HttpClient.newBuilder().build();

        var uri = "http://localhost:8080/weather?city=" + city + "&type=stat";
        var request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(uri))
                .build();


        var response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());

        WeatherStatDto weatherStatDto;
        weatherStatDto = objectMapper.readValue(response.body(), WeatherStatDto.class);

        return "City - " + weatherStatDto.getCity() + "\n" +
                "Date - " + weatherStatDto.getDate() + "\n" +
                "Temperature - " + weatherStatDto.getTemperature() + "\n" +
                "Wind speed - " + weatherStatDto.getWindSpeed();

    }

    public String getAverageWeather(String city) throws IOException, InterruptedException {
        var client = HttpClient.newBuilder().build();

        var uri = "http://localhost:8080/weather?city=" + city + "&type=average";
        var request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(uri))
                .build();


        var response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());

        WeatherAverageDto weatherAverageDto;
        weatherAverageDto = objectMapper.readValue(response.body(), WeatherAverageDto.class);

        return "City - " + weatherAverageDto.getCity() + "\n" +
                "Temperature - " + weatherAverageDto.getTemperature() + "\n" +
                "Wind speed - " + weatherAverageDto.getWindSpeed();

    }
}
