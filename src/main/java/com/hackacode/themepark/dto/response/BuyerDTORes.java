package com.hackacode.themepark.dto.response;

import com.hackacode.themepark.model.TicketDetail;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Hidden
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuyerDTORes {

    private Long id;
    private String dni;
    private String name;
    private String surname;
    private LocalDate birthdate;
    private int age;
    private String lastVisit;
    private boolean isBanned;

}
