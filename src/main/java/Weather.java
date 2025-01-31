import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Weather {

    public static String token;

    public static void main(String[] args) {

        System.out.println("Вас приветствует прогноз погоды!");

        Scanner scanner = new Scanner(System.in);

        while (true) {

            System.out.println("Выберите одну из команд:");
            System.out.println("1. Ввести новый токен");
            System.out.println("2. Запросить прогноз погоды");
            System.out.println("3. Завершить работу");

            try {

                int actionNumber = scanner.nextInt();

                if (actionNumber == 1) {
                    System.out.println("Введите токен");
                    token = scanner.next().trim();
                    scanner.nextLine();
                } else if (actionNumber == 2) {

                    if (token.isEmpty()) {
                        System.out.println("Сначала нужно ввести токен");
                        continue;
                    }
                    System.out.println("Введите город");
                    String city = scanner.next().trim();
                    scanner.nextLine();
                    String answer = getWeather(token, city);
                    System.out.println(answer);

                } else if (actionNumber == 3) {
                    break;
                } else {
                    System.out.println("Неизвестная команда!");
                }

            } catch (Exception e) {
                System.out.println("Произошла ошибка по причине: " + e.getMessage());
            }


        }

    }

    private static String getWeather(String accessToken, String city) throws IOException, InterruptedException {
        var client = HttpClient.newBuilder().build();

        var uri = "http://api.weatherstack.com/current?access_key=" + accessToken + "&query=" + city;
        var request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(uri))
                .build();

        var response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());

        ObjectMapper objectMapper = new ObjectMapper();

        WeatherResponse weatherResponse;
        weatherResponse = objectMapper.readValue(response.body(), WeatherResponse.class);

        return "Country - " + weatherResponse.getLocation().getCountry() + "\n" +
                "Location - " + weatherResponse.getLocation().getName() + "\n" +
                "Temperature - " + weatherResponse.getCurrent().getTemperature() + "\n" +
                "Wind speed - " + weatherResponse.getCurrent().getWindSpeed();

    }
}
