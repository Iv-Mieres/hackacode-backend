package com.hackacode.themepark.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleDTOReq {

    private Long id;
    @NotNull(message = "No puede estar vacio")
    private LocalTime startTime;
    @NotNull(message = "No puede estar vacio")
    private LocalTime endTime;

}
