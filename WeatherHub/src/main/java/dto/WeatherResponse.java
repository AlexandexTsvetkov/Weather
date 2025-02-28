package dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class WeatherResponse {

    private Request request;
    private Location location;
    private Current current;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Request {

        private String type;
        private String query;
        private String language;
        private String unit;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Location {

        private String name;
        private String country;
        private String region;
        private String lat;
        private String lon;
        @JsonProperty("timezone_id")
        private String timezoneId;
        private String localtime;
        @JsonProperty("localtime_epoch")
        private long localtimeEpoch;
        @JsonProperty("utc_offset")
        private String utcOffset;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Current {

        @JsonProperty("observation_time")
        private String observationTime;
        private int temperature;
        @JsonProperty("weather_code")
        private int weatherCode;
        @JsonProperty("weather_icons")
        private String[] weatherIcons;
        @JsonProperty("weather_descriptions")
        private String[] weatherDescriptions;
        @JsonProperty("wind_speed")
        private int windSpeed;
        @JsonProperty("wind_degree")
        private int windDegree;
        @JsonProperty("wind_dir")
        private String windDir;
        private int pressure;
        private int precip;
        private int humidity;
        private int cloudcover;
        private int feelslike;
        @JsonProperty("uv_index")
        private int uvIndex;
        private int visibility;
        @JsonProperty("is_day")
        private String isDay;
    }
}
