package com.hackacode.themepark.dto.response;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Hidden
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportDTORes {

    private Long totalTicketsSold;
    private Double totalAmountSaleDay;
    private Double totalAmountSaleMonthAndYear;
    private Double totalAmountSaleYear;
    private List<EmployeeDTORes> employees;
    private BuyerDTORes buyer;
    private String gameName;
}
