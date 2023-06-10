package com.hackacode.themepark.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleDTORes {

    private Long saleId;
    private double totalPrice;
    private List<NormalTicketDTORes> ticketDTOList;
    private List<VipTicketDTORes> ticketVipDTOList;

}
