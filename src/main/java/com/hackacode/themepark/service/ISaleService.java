package com.hackacode.themepark.service;

import com.hackacode.themepark.dto.response.SaleDTORes;
import com.hackacode.themepark.model.Sale;

import java.util.List;

public interface ISaleService {

    void saveSale(Sale sale);
    SaleDTORes getSaleById(Long saleId);
    List<SaleDTORes> getSales();
    void updateSale(Long saleId);
    void deleteSale(Long saleId);

}
