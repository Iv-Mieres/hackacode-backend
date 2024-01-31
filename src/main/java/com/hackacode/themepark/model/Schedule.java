package com.hackacode.themepark.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "operatingHours")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Temporal(TemporalType.TIME)
    private LocalTime startTime;
    @Temporal(TemporalType.TIME)
    private LocalTime endTime;
    @OneToMany(mappedBy = "schedule")
    @JsonIgnoreProperties("schedule")
    private List<Game> games;
}
