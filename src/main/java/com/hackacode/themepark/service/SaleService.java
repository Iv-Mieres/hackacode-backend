package com.hackacode.themepark.service;

import com.hackacode.themepark.model.Sale;
import com.hackacode.themepark.repository.ISaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleService implements ISaleService{

    private ISaleRepository saleRepository;

    @Override
    public void saveSale(Sale sale) {
        saleRepository.save(sale);
    }

    @Override
    public Sale getSaleById(Long saleId) {
        return null;
    }

    @Override
    public List<Sale> getSales() {
        return null;
    }

    @Override
    public void updateSale(Long saleId) {

    }

    @Override
    public void deleteSale(Long saleId) {

    }
}
