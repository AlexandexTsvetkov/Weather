package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import context.SingleApp;
import dto.WeatherAverageDto;
import dto.WeatherStatDto;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.WeatherService;

@WebServlet("/weather")
public class WeatherServlet extends HttpServlet {

    private WeatherService weatherService;
    private ObjectMapper objectMapper;

    public void init() {
        weatherService = SingleApp.get().getWeatherService();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {

        String city = req.getParameter("city");
        String typeParameter = req.getParameter("type");

        String type = null;
        if ("stat".equals(typeParameter)) {
            type = "stat";
        } else if ("average".equals(typeParameter)) {
            type = "average";
        }

        if (type == null) {
            type = "stat";
        }

        try {

        if (type.equals("average")) {

            WeatherAverageDto averageWeather = weatherService.getWeatherAverage(city);

            if (averageWeather != null) {
                resp.setStatus(200);
                resp.setContentType("application/json");

                String jsonResponse = objectMapper.writeValueAsString(averageWeather);
                resp.getWriter().write(jsonResponse);
            } else {

                resp.setStatus(404);
                resp.getWriter().write("{Город не найден}");
            }
        } else {

            WeatherStatDto weatherStatDto = weatherService.getWeatherStat(city);

            resp.setStatus(200);
            resp.setContentType("application/json");

            if (weatherStatDto != null) {
                resp.setStatus(200);
                resp.setContentType("application/json");

                String jsonResponse = objectMapper.writeValueAsString(weatherStatDto);
                resp.getWriter().write(jsonResponse);
            } else {

                resp.setStatus(404);
                resp.getWriter().write("{Город не найден}");
            }

        }
        } catch (Exception e) {
            resp.setStatus(500);
        }
    }
}
