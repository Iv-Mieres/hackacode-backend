package com.hackacode.themepark.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameDTORes {

    private Long id;
    private String name;
    private double price;
    private int requiredAge;
    private ScheduleDTORes schedule;

}
