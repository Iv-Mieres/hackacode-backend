package com.hackacode.themepark.service;

import com.hackacode.themepark.dto.request.BuyerDTOReq;
import com.hackacode.themepark.dto.response.BuyerDTORes;

import java.util.List;


public interface IBuyerService {

    void saveBuyer(BuyerDTOReq buyerDTO);
    BuyerDTORes getBuyerById(Long buyerId);
    List<BuyerDTORes> getAllBuyers();
    void updateBuyer(BuyerDTOReq buyerDTO);
    void deleteBuyer(Long buyerID);
}
