package com.hackacode.themepark.controller;

import com.hackacode.themepark.model.Sale;
import com.hackacode.themepark.service.ISaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sale")
@RequiredArgsConstructor
public class SaleController {

    private ISaleService saleService;

}
