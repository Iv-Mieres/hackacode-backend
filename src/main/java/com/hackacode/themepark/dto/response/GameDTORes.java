package com.hackacode.themepark.dto.response;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Hidden
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameDTORes {

    private Long id;
    private String name;
    private int requiredAge;
    private ScheduleDTORes schedule;

}
