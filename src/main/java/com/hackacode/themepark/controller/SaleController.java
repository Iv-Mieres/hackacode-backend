package com.hackacode.themepark.controller;

import com.hackacode.themepark.dto.request.EmployeeDTOReq;
import com.hackacode.themepark.dto.request.SaleDTOReq;
import com.hackacode.themepark.dto.response.EmployeeDTORes;
import com.hackacode.themepark.dto.response.SaleDTORes;
import com.hackacode.themepark.model.Sale;
import com.hackacode.themepark.service.ISaleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sale")
public class SaleController {


    private ISaleService saleService;

    @PostMapping("/")
    public ResponseEntity<HttpStatus> saveSale(@Valid @RequestBody SaleDTOReq saleDTOReq){
        saleService.saveSale(saleDTOReq);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public SaleDTORes getSale(@PathVariable Long id) {
        return saleService.getSaleById(id);
    }

    @GetMapping("/ver_todos")
    public ResponseEntity<Page<SaleDTORes>> getAllSales(Pageable pageable){
        return ResponseEntity.ok(saleService.getSales(pageable));
    }

    @PutMapping("/")
    public ResponseEntity<HttpStatus> updateSale ( @RequestBody SaleDTOReq saleDTOReq) throws Exception {
        saleService.updateSale(saleDTOReq);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteSale(@PathVariable Long id) throws Exception {
        saleService.deleteSale(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
