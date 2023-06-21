package com.hackacode.themepark.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportDTORes {

    private Long totalNormalTicketsSold;
    private Long totalVipTicketsSold;
    private Double totalAmountSaleDay;
    private Double totalAmountSaleMonthAndYear;
    private Double totalAmountSaleYear;
    private List<EmployeeDTORes> employees;
    private BuyerDTORes buyer;
    private String gameName;
}
