package mapper;

import dao.WeatherRequestDao;
import dao.WeatherResponseDao;
import dto.WeatherResponse;
import dto.WeatherStatDto;

import java.time.Instant;

public class WeatherMapper {

    public static WeatherResponseDao mapToWeatherResponseDao(WeatherResponse weatherResponse, WeatherRequestDao weatherRequestDao) {

        WeatherResponseDao weatherResponseDao = new WeatherResponseDao();
        weatherResponseDao.setWeatherRequest(weatherRequestDao);
        weatherResponseDao.setCreated(Instant.now());
        weatherResponseDao.setCountry(weatherResponse.getLocation().getCountry());
        weatherResponseDao.setLocation(weatherResponse.getLocation().getName());
        weatherResponseDao.setTemperature(weatherResponse.getCurrent().getTemperature());
        weatherResponseDao.setWindSpeed(weatherResponse.getCurrent().getWindSpeed());

        return weatherResponseDao;
    }

    public static WeatherStatDto mapToWeatherStatDto(WeatherResponseDao weatherResponseDao) {

        WeatherStatDto weatherStatDto = new WeatherStatDto();
        weatherStatDto.setCity(weatherResponseDao.getLocation());
        weatherStatDto.setDate(weatherResponseDao.getCreated());
        weatherStatDto.setTemperature(weatherResponseDao.getTemperature());
        weatherStatDto.setWindSpeed(weatherResponseDao.getWindSpeed());

        return weatherStatDto;
    }
}
