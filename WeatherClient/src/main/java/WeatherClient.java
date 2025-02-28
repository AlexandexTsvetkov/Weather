import service.WeatherClientService;

import java.util.Scanner;

public class WeatherClient {

    public static void main(String[] args) {

        System.out.println("Вас приветствует прогноз погоды!");

        Scanner scanner = new Scanner(System.in);

        WeatherClientService service = new WeatherClientService();

        while (true) {

            System.out.println("Выберите одну из команд:");
            System.out.println("1. Запросить статистику по городу");
            System.out.println("2. Завершить средние значения по городу");
            System.out.println("3. Завершить работу");

            try {

                int actionNumber = scanner.nextInt();

                if (actionNumber == 1) {

                    System.out.println("Введите город");
                    String city = scanner.next().trim();
                    String answer = service.getStatWeather(city);
                    System.out.println(answer);
                    scanner.nextLine();
                } else if (actionNumber == 2) {

                    System.out.println("Введите город");
                    String city = scanner.next().trim();
                    scanner.nextLine();
                    String answer = service.getAverageWeather(city);
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
