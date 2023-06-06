package com.hackacode.themepark.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "No puede estar vacio")
    @Temporal(TemporalType.TIME)
    private LocalTime startTime;
    @NotNull(message = "No puede estar vacio")
    @Temporal(TemporalType.TIME)
    private LocalTime endTime;
    @OneToMany(mappedBy = "schedule")
    @JsonIgnoreProperties("schedule")
    private List<Game> games;
}
