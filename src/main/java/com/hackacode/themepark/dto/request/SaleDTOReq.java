package com.hackacode.themepark.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleDTOReq {
    private Long id;
    @NotNull(message = "Debe asignar un ticket detallado")
    private List<TicketDetailDTOReq> ticketsDetail;
    @NotNull(message = "Debe asignar un juego")
    private GameDTOReq game;
}
