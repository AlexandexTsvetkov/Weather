package repository;

import java.sql.*;
import java.util.UUID;

public class WeatherRepository {

    public static UUID insertWeatherRequest(String city) {

        String query = "INSERT INTO request (city) VALUES (?) RETURNING id";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, city);

            UUID generatedId = null;

            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    generatedId = resultSet.getObject(1, UUID.class);
                }
            }

            return generatedId;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void insertWeatherResponse(UUID requestId, String country, String location, int temperature, int windspeed) {

        String query = "INSERT INTO response (request_id, country, location, temperature, windspeed) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setObject(1, requestId);
            ps.setString(2, country);
            ps.setString(3, location);
            ps.setInt(4, temperature);
            ps.setInt(5, windspeed);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static Connection getConnection() throws SQLException  {

        Connection connection;
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/weather_db", "dbuser", "password");
        return connection;
    }

    public static void dbInit() {

        String query = "CREATE TABLE IF NOT EXISTS request ("
                + "id UUID DEFAULT gen_random_uuid() NOT NULL PRIMARY KEY, "
                + "city VARCHAR(100), "
                + "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
                + ");";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        query = "create table IF NOT EXISTS response(" +
                "    id          UUID DEFAULT gen_random_uuid() NOT NULL PRIMARY KEY, " +
                "    request_id  uuid " +
                "        constraint response_request_id_fk " +
                "            references request, " +
                "    country     varchar(100) not null, " +
                "    location    varchar(100),\n" +
                "    temperature integer      not null, " +
                "    windspeed   integer      not null, " +
                "    created_ad  TIMESTAMP DEFAULT CURRENT_TIMESTAMP " +
                ");";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
