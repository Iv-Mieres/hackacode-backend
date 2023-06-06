package com.hackacode.themepark.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameDTORes {

    private Long gameId;
    private String name;
    private double price;
    private List<ScheduleDTORes> schedulesDTO;

}
