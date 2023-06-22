package com.hackacode.themepark.service;

import com.hackacode.themepark.dto.request.BuyerDTOReq;
import com.hackacode.themepark.dto.response.BuyerDTORes;
import com.hackacode.themepark.exception.IdNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IBuyerService {

    void saveBuyer(BuyerDTOReq buyerDTO) throws IdNotFoundException;
    BuyerDTORes getBuyerById(Long buyerId) throws IdNotFoundException;
    Page<BuyerDTORes> getAllBuyers(Pageable pageable);
    void updateBuyer(BuyerDTORes buyerDTO) throws Exception;
    void deleteBuyer(Long buyerID) throws IdNotFoundException;
}
