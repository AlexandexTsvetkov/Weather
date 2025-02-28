package dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
public class WeatherStatDto {

    private Instant date;
    private String city;
    private Integer temperature;
    private Integer windSpeed;
}
