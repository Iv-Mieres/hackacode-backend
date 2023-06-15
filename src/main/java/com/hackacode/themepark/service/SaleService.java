package com.hackacode.themepark.service;


import com.hackacode.themepark.dto.response.SaleDTORes;
import com.hackacode.themepark.model.Sale;
import com.hackacode.themepark.repository.ISaleRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleService implements ISaleService{

    private ISaleRepository saleRepository;
    private ModelMapper modelMapper;

    @Override
    public void saveSale(Sale sale) {
        saleRepository.save(sale);
    }

    @Override
    public SaleDTORes getSaleById(Long saleId) {
        var saleDB = saleRepository.findById(saleId).orElse(null);
        return modelMapper.map(saleDB, SaleDTORes.class);
    }

    @Override
    public List<SaleDTORes> getSales() {
        return null;
    }

    @Override
    public void updateSale(Long saleId) {

    }

    @Override
    public void deleteSale(Long saleId) {

    }
}
