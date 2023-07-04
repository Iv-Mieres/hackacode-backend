package com.hackacode.themepark.controller;

import com.hackacode.themepark.dto.request.SaleDTOReq;
import com.hackacode.themepark.dto.response.ReportDTORes;
import com.hackacode.themepark.dto.response.SaleDTORes;
import com.hackacode.themepark.exception.IdNotFoundException;
import com.hackacode.themepark.service.ISaleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ventas")
public class SaleController {


    private final ISaleService saleService;

    @PostMapping()
    public ResponseEntity<HttpStatus> saveSale(@Valid @RequestBody SaleDTOReq saleDTOReq) throws Exception {
        saleService.saveSale(saleDTOReq);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SaleDTORes> getSale(@PathVariable Long id) throws IdNotFoundException {

        return ResponseEntity.ok(saleService.getSaleById(id));
    }

    @GetMapping
    public ResponseEntity<Page<SaleDTORes>> getAllSales(Pageable pageable) {
        return ResponseEntity.ok(saleService.getSales(pageable));
    }

    @PutMapping()
    public ResponseEntity<HttpStatus> updateSale(@Valid @RequestBody SaleDTOReq saleDTOReq) throws Exception {
        saleService.updateSale(saleDTOReq);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteSale(@PathVariable Long id) throws Exception {
        saleService.deleteSale(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

//    @GetMapping("/monto_total_por_dia")
//    public ResponseEntity<ReportDTORes> getAllSalesPerDay(@RequestParam
//                                                          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
//        return ResponseEntity.ok(saleService.sumTotalAmountOfAGivenDay(date));
//    }
//
//    @GetMapping("/monto_total_por_mes")
//    public ResponseEntity<ReportDTORes> getAllSalesPerMonth(@RequestParam int year, int month) {
//        return ResponseEntity.ok(saleService.sumTotalAmountOfAGivenMonth(year, month));
//    }
}
