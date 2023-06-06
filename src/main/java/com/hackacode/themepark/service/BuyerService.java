package com.hackacode.themepark.service;

import com.hackacode.themepark.dto.request.BuyerDTOReq;
import com.hackacode.themepark.dto.response.BuyerDTORes;
import com.hackacode.themepark.repository.IBuyerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuyerService implements IBuyerService {

    @Autowired
    private IBuyerRepository buyerRepository;

    @Override
    public void saveBuyer(BuyerDTOReq buyerDTO){
    }

    @Override
    public BuyerDTORes getBuyerById(Long buyerId) {
        return null;
    }

    @Override
    public List<BuyerDTORes> getAllBuyers() {
        return null;
    }

    @Override
    public void updateBuyer(BuyerDTOReq buyerDTO) {

    }

    @Override
    public void deleteBuyer(Long buerId) {

    }
}
