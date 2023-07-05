package com.hackacode.themepark.dto.response;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Hidden
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportGameDTORes {

    private String game;
    private Long totalTicketsSold;

}
