package com.hackacode.themepark.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameDTOReq {

    private Long id;
    private String name;
    private int requiredAge;
    private ScheduleDTOReq schedule;

}
