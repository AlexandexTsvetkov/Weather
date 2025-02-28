package dao;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "response")
public class WeatherResponseDao {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String country;
    private String location;
    private Integer temperature;
    private Integer windSpeed;

    @Column(name = "created_at")
    private Instant created;

    @OneToOne
    @JoinColumn(name = "request_id", referencedColumnName = "id")
    private WeatherRequestDao weatherRequest;

}