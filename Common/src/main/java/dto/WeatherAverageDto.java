package dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class WeatherAverageDto {

    private String city;
    private Integer temperature;
    private Integer windSpeed;
}
