package com.hackacode.themepark.controller;

import com.hackacode.themepark.dto.request.BuyerDTOReq;
import com.hackacode.themepark.dto.response.BuyerDTORes;
import com.hackacode.themepark.exception.DniExistsException;
import com.hackacode.themepark.exception.IdNotFoundException;
import com.hackacode.themepark.repository.ITicketRepository;
import com.hackacode.themepark.service.IBuyerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name="Controlador de Clientes")
@RestController
@RequestMapping("/api/compradores")
public class BuyerController {

    @Autowired
    private IBuyerService buyerServer;

    @Autowired
    private ITicketRepository normalTicketRepository;

    @Operation(
            summary = "Guarda un cliente",
            description = "Guarda el cliente y devuelve un Codigo de estado 201 creado"
    )
    @PostMapping()
    public ResponseEntity<HttpStatus> saveBuyer(@Valid @RequestBody BuyerDTOReq buyerDTO) throws DniExistsException {
        buyerServer.saveBuyer(buyerDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(
            summary = "Trae un cliente",
            description = "Busca un cliente por id y devuelve un Codigo de estado 200 y los datos del cliente"
    )
    @GetMapping("/{buyerId}")
    public ResponseEntity<BuyerDTORes> getBuyer(@PathVariable Long buyerId) throws IdNotFoundException {
        return ResponseEntity.ok(buyerServer.getBuyerById(buyerId));
    }

    @Operation(
            summary = "Traer todos los clientes",
            description = "Trae todos los clientes de base de datos y devuelve un Codigo de estado 200 y el listado de clientes"
    )
    @GetMapping()
    public ResponseEntity<Page<BuyerDTORes>> getAllBuyers(Pageable pageable){
        return ResponseEntity.ok(buyerServer.getAllBuyers(pageable));
    }

    @Operation(
            summary = "Actualiza un cliente",
            description = "Busca un cliente por id y lo actualiza, devuelve un Codigo de estado 204"
    )
    @PutMapping()
    public ResponseEntity<HttpStatus> updateBuyer(@Valid @RequestBody BuyerDTOReq buyerDTO) throws IdNotFoundException, DniExistsException {
        buyerServer.updateBuyer(buyerDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(
            summary = "Elimina un cliente",
            description = "Elimina de forma logica un cliente por id, devuelve un Codigo de estado 204"
    )
    @DeleteMapping("/{buyerId}")
    public ResponseEntity<HttpStatus> deleteBuyer(@PathVariable Long buyerId) throws IdNotFoundException {
        buyerServer.deleteBuyer(buyerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
