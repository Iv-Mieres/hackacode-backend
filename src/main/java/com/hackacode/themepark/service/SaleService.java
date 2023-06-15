package com.hackacode.themepark.service;


import com.hackacode.themepark.dto.request.SaleDTOReq;
import com.hackacode.themepark.dto.response.SaleDTORes;
import com.hackacode.themepark.model.Sale;
import com.hackacode.themepark.repository.ISaleRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class SaleService implements ISaleService{

    private ISaleRepository saleRepository;
    private ModelMapper modelMapper;

    @Override
    public void saveSale(SaleDTOReq sale) {
        saleRepository.save(modelMapper.map(sale, Sale.class));
    }

    @Override
    public SaleDTORes getSaleById(Long saleId) {
        var saleDB = saleRepository.findById(saleId).orElse(null);
        return modelMapper.map(saleDB, SaleDTORes.class);
    }

    @Override
    public Page<SaleDTORes> getSales(Pageable pageable) {
        var sales = saleRepository.findAll(pageable);
        var salesDTO = new ArrayList<SaleDTORes>();

        for (Sale sale: sales) {
            salesDTO.add(modelMapper.map(sale, SaleDTORes.class));
        }
        return new PageImpl<>(salesDTO, pageable, salesDTO.size());
    }

    @Override
    public void updateSale(SaleDTOReq saleDTOReq) throws Exception {
        var saleUpdate = modelMapper.map(saleDTOReq, Sale.class);
        saleRepository.save(saleUpdate);
    }

    @Override
    public void deleteSale(Long saleId) {
        saleRepository.deleteById(saleId);
    }
}
