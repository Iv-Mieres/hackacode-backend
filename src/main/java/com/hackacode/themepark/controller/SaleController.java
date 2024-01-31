package com.hackacode.themepark.controller;

import com.hackacode.themepark.dto.request.SaleDTOReq;
import com.hackacode.themepark.dto.response.SaleDTORes;
import com.hackacode.themepark.exception.IdNotFoundException;
import com.hackacode.themepark.service.ISaleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Controlador de Venta")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ventas")
public class SaleController {


    private final ISaleService saleService;

    @Operation(
            summary = "Guarda una venta",
            description = "Guarda la venta y devuelve un Codigo de estado 201 creado"
    )
    @PostMapping()
    public ResponseEntity<HttpStatus> saveSale(@Valid @RequestBody SaleDTOReq saleDTOReq) throws Exception {
        saleService.saveSale(saleDTOReq);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(
            summary = "Trae una venta",
            description = "Busca una venta por id y devuelve un Codigo de estado 200 y los datos del venta"
    )
    @GetMapping("/{id}")
    public ResponseEntity<SaleDTORes> getSale(@PathVariable Long id) throws IdNotFoundException {

        return ResponseEntity.ok(saleService.getSaleById(id));
    }

    @Operation(
            summary = "Traer todos los ventas",
            description = "Trae todas los ventas de base de datos y devuelve un Codigo de estado 200 y el listado de ventas"
    )
    @GetMapping
    public ResponseEntity<Page<SaleDTORes>> getAllSales(Pageable pageable) {
        return ResponseEntity.ok(saleService.getSales(pageable));
    }

    @Operation(
            summary = "Actualiza una venta",
            description = "Busca una venta por id y lo actualiza, devuelve un Codigo de estado 204"
    )
    @PutMapping()
    public ResponseEntity<HttpStatus> updateSale(@Valid @RequestBody SaleDTOReq saleDTOReq) throws Exception {
        saleService.updateSale(saleDTOReq);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(
            summary = "Elimina una venta",
            description = "Elimina de forma logica una venta por id, devuelve un Codigo de estado 204"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteSale(@PathVariable Long id){
        saleService.deleteSale(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
