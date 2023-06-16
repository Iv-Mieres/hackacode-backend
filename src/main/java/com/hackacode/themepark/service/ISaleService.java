package com.hackacode.themepark.service;

import com.hackacode.themepark.dto.request.SaleDTOReq;
import com.hackacode.themepark.dto.response.SaleDTORes;
import com.hackacode.themepark.model.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ISaleService {

    void saveSale(SaleDTOReq sale);
    SaleDTORes getSaleById(Long saleId);
    Page<SaleDTORes> getSales(Pageable pageable);
    void updateSale(SaleDTOReq saleDTOReq) throws Exception;
    void deleteSale(Long saleId);

}
