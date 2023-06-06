package com.hackacode.themepark.controller;

import com.hackacode.themepark.dto.request.BuyerDTOReq;
import com.hackacode.themepark.service.IBuyerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/buyer")
public class BuyerController {

    @Autowired
    private IBuyerService buyerServer;

    @PostMapping()
    public void saveBuyer(@Valid @RequestBody BuyerDTOReq buyerDTO){
        buyerServer.saveBuyer(buyerDTO);
    }

}
