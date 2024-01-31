package com.hackacode.themepark.dto.response;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Hidden
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleDTORes {

    private Long id;
    private LocalTime startTime;
    private LocalTime endTime;

}
