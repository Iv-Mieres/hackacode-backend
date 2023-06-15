package com.hackacode.themepark.controller;

import com.hackacode.themepark.dto.request.BuyerDTOReq;
import com.hackacode.themepark.dto.response.BuyerDTORes;
import com.hackacode.themepark.service.IBuyerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@PreAuthorize("hasRole('ADMINISTRADOR')")
@RequestMapping("/api/compradores")
public class BuyerController {

    @Autowired
    private IBuyerService buyerServer;

    @PostMapping()
    public ResponseEntity<HttpStatus> saveBuyer(@Valid @RequestBody BuyerDTOReq buyerDTO) throws Exception {
        buyerServer.saveBuyer(buyerDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{buyerId}")
    public ResponseEntity<BuyerDTORes> getBuyer(@PathVariable Long buyerId) throws Exception {
        return ResponseEntity.ok(buyerServer.getBuyerById(buyerId));
    }

    @GetMapping()
    public ResponseEntity<Page<BuyerDTORes>> getAllBuyers(Pageable pageable){
        return ResponseEntity.ok(buyerServer.getAllBuyers(pageable));
    }

    @PutMapping()
    public ResponseEntity<HttpStatus> updateBuyer(@Valid @RequestBody BuyerDTORes buyerDTO) throws Exception {
        buyerServer.updateBuyer(buyerDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{buyerId}")
    public ResponseEntity<HttpStatus> deleteBuyer(@PathVariable Long buyerId) throws Exception {
        buyerServer.deleteBuyer(buyerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
