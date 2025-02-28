package context;

import repository.WeatherRepository;

public class SingleApp {

    private static App app;

    public static synchronized App get() {

        if(app != null) return app;

        WeatherRepository weatherRepository = new WeatherRepository();
        Context c = new Context(weatherRepository);

        app = new App(c);

        return app;
    }

}