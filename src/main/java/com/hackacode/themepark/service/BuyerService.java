package com.hackacode.themepark.service;

import com.hackacode.themepark.dto.request.BuyerDTOReq;
import com.hackacode.themepark.dto.response.BuyerDTORes;
import com.hackacode.themepark.exception.IdNotFoundException;
import com.hackacode.themepark.model.Buyer;
import com.hackacode.themepark.repository.IBuyerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class BuyerService implements IBuyerService {

    @Autowired
    private IBuyerRepository buyerRepository;

    @Autowired
    private ModelMapper modelMapper;

    //CREA UN COMPRADOR
    @Override
    public void saveBuyer(BuyerDTOReq buyerDTO) throws IdNotFoundException {
        //Comprueba que el dni sea unico
        if(buyerRepository.existsByDni(buyerDTO.getDni())){
            throw new IdNotFoundException("El dni " + buyerDTO.getDni() + " ya existe. Ingrese un nuevo dni");
        }
        buyerRepository.save(modelMapper.map(buyerDTO, Buyer.class));
    }

    //BUSCA COMPRADOR POR ID
    @Override
    public BuyerDTORes getBuyerById(Long buyerId) throws IdNotFoundException {
        return modelMapper.map(buyerRepository.findById(buyerId)
                .orElseThrow(() -> new IdNotFoundException("El id " + buyerId + " no existe")), BuyerDTORes.class);
    }

    //LISTA DTO DE COMPRADORES PAGINADOS
    @Override
    public Page<BuyerDTORes> getAllBuyers(Pageable pageable) {
        //recorre la lista de compradores, los convierte a DTO y los guarda en la List buyersDTO
        var buyersDTO = new ArrayList<BuyerDTORes>();
        for (Buyer buyer:  buyerRepository.findAll(pageable)) {
            //convierte el comprador a DTO y lo guarda en la List
            buyersDTO.add(modelMapper.map(buyer, BuyerDTORes.class));
        }
        return new PageImpl<>(buyersDTO, pageable, buyersDTO.size()) ;
    }

    //MODIFICA DATOS DEL COMPRADOR
    @Override
    public void updateBuyer(BuyerDTORes buyerDTO) throws Exception {
        var buyerBD = buyerRepository.findById(buyerDTO.getId())
                .orElseThrow(() -> new IdNotFoundException("El id " + buyerDTO.getId() + " no existe"));
        //Si el dni ingresado ya existe en la BD y no pertenece al comprador ingresado lanza una Exception
        this.validateIfExistsByDni(buyerDTO.getDni(), buyerBD.getDni());
        buyerRepository.save( modelMapper.map(buyerBD, Buyer.class));
    }

    //ELIMINA UN COMPRADOR POR ID
    @Override
    public void deleteBuyer(Long buyerId) throws IdNotFoundException {
        var buyerBD = buyerRepository.findById(buyerId)
                .orElseThrow(() -> new IdNotFoundException("El id " + buyerId + " no existe"));
        buyerBD.setBanned(true);
        buyerRepository.save(buyerBD);
    }

    // Método de validación para el UPDATE

    public void validateIfExistsByDni(String BuyerDTODni, String buyerBDDni) throws Exception {
        if(!BuyerDTODni.equals(buyerBDDni) && buyerRepository.existsByDni(BuyerDTODni)){
            throw new Exception("El dni " + BuyerDTODni + " ya existe. Ingrese un nuevo dni");
        }
    }


}
