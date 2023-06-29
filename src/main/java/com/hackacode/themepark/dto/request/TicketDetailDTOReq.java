package com.hackacode.themepark.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketDetailDTOReq {

    private UUID id;
    @NotNull(message = "Debe asignar un ticket")
    private TicketDTOReq ticket;
    @NotNull(message = "Debe asignar un comprador")
    private BuyerDTOReq buyer;
}
