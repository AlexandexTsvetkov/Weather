package context;

import lombok.Getter;
import service.WeatherService;

@Getter
public class App {

    private final WeatherService weatherService;

    public App(Context c) {

        c.root = this;

        this.weatherService = new WeatherService(c);

    }
}
