package com.hackacode.themepark.service;

import com.hackacode.themepark.model.Sale;

import java.util.List;

public interface ISaleService {

    void saveSale(Sale sale);
    Sale getSaleById(Long saleId);
    List<Sale> getSales();
    void updateSale(Long saleId);
    void deleteSale(Long saleId);

}
