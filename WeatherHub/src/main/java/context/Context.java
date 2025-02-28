package context;

import repository.WeatherRepository;

public class Context {

    public final WeatherRepository weatherRepository;

    public App root;

    public Context(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

}
