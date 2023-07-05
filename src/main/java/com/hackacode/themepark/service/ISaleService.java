package com.hackacode.themepark.service;

import com.hackacode.themepark.dto.request.SaleDTOReq;
import com.hackacode.themepark.dto.response.SaleDTORes;
import com.hackacode.themepark.exception.IdNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ISaleService {

    void saveSale(SaleDTOReq saleDTOReq) throws Exception;
    SaleDTORes getSaleById(Long saleId) throws IdNotFoundException;
    Page<SaleDTORes> getSales(Pageable pageable);
    void updateSale(SaleDTOReq saleDTOReq) throws IdNotFoundException;
    void deleteSale(Long saleId);

}
