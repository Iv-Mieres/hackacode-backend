package com.hackacode.themepark.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleDTOReq {
    private Long id;
    private List<VipTicketDTOReq> vipTickets;
    private List<NormalTicketDTOReq> normalTickets;
}
