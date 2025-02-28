import context.SingleApp;
import controller.WeatherServlet;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Wrapper;
import org.apache.catalina.startup.Tomcat;
import service.WeatherService;

import java.util.Scanner;

public class Weather {

    public static String token = "f9b4ffde74b2860db5e2329ca98528ca";
    public static final WeatherService weatherService = SingleApp.get().getWeatherService();
    private static final int PORT = 8080;

    public static void main(String[] args) throws LifecycleException {

        Tomcat tomcat = new Tomcat();
        tomcat.getConnector().setPort(PORT);

        Context tomcatContext = tomcat.addContext("", null);

        Wrapper testServletWrapper =
                Tomcat.addServlet(tomcatContext, "testServlet", new WeatherServlet());

        testServletWrapper.addMapping("/weather");

        tomcat.start();

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
                    String answer = weatherService.getWeather(token, city);
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

}
