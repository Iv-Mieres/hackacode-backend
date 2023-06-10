package com.hackacode.themepark.service;

import com.hackacode.themepark.dto.request.BuyerDTOReq;
import com.hackacode.themepark.dto.response.BuyerDTORes;
import com.hackacode.themepark.model.Buyer;
import com.hackacode.themepark.repository.IBuyerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuyerService implements IBuyerService {

    @Autowired
    private IBuyerRepository buyerRepository;

    @Autowired
    private ModelMapper modelMapper;

    public BuyerService() {
        this.modelMapper = new ModelMapper();
    }

    @Override
    public void saveBuyer(BuyerDTOReq buyerDTO){
        buyerRepository.save(modelMapper.map(buyerDTO, Buyer.class));
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
