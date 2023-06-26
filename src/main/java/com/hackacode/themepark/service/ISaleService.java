package com.hackacode.themepark.service;

import com.hackacode.themepark.dto.request.SaleDTOReq;
import com.hackacode.themepark.dto.response.ReportDTORes;
import com.hackacode.themepark.dto.response.SaleDTORes;
import com.hackacode.themepark.exception.IdNotFoundException;
import com.hackacode.themepark.model.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public interface ISaleService {

    void saveSale(SaleDTOReq saleDTOReq) throws Exception;
    SaleDTORes getSaleById(Long saleId) throws IdNotFoundException;
    Page<SaleDTORes> getSales(Pageable pageable);
    void updateSale(SaleDTOReq saleDTOReq) throws IdNotFoundException;
    void deleteSale(Long saleId) throws IdNotFoundException;

    ReportDTORes sumTotalAmountOfAGivenDay(LocalDate date);

    //SUMA DE MONTO TOTAL DE UN DETERMINADO DÍA - SIN TERMINAR
    ReportDTORes sumTotalAmountOfAGivenMonth(int year , int month);
}
