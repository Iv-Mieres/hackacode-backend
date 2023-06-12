package com.hackacode.themepark.service;

import com.hackacode.themepark.dto.request.BuyerDTOReq;
import com.hackacode.themepark.dto.response.BuyerDTORes;
import com.hackacode.themepark.model.Buyer;
import com.hackacode.themepark.repository.IBuyerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.util.ArrayList;
import java.util.List;

@Service
public class BuyerService implements IBuyerService {

    @Autowired
    private IBuyerRepository buyerRepository;

    @Autowired
    private ModelMapper modelMapper;

    // CREAR Y GUARDAR COMPRADOR
    @Override
    public void saveBuyer(BuyerDTOReq buyerDTO) throws Exception {
        //Comprueba que el dni sea unico
        if(buyerRepository.existsByDni(buyerDTO.getDni())){
            throw new Exception("El dni " + buyerDTO.getDni() + " ya existe. Ingrese un nuevo dni");
        }
        buyerRepository.save(modelMapper.map(buyerDTO, Buyer.class));
    }

    //BUSCAR COMPRADOR POR ID
    @Override
    public BuyerDTORes getBuyerById(Long buyerId) throws Exception {
        //se busca el comprador en la base de datos
        var buyer = buyerRepository.findById(buyerId)
                .orElseThrow(() -> new Exception("El id " + buyerId + " no existe"));
        //Se retorna el comprador DTO con los datos
        return modelMapper.map(buyer, BuyerDTORes.class);
    }

    //LISTAR DTO DE COMPRADORES PAGINADOS
    @Override
    public Page<BuyerDTORes> getAllBuyers(Pageable pageable) {
        var buyers = buyerRepository.findAll(pageable);

        //recorre la lista de compradores, los convierte a DTO y los guarda en la List buyersDTO
        var buyersDTO = new ArrayList<BuyerDTORes>();
        for (Buyer buyer: buyers) {
            //convierte el comprador a DTO y lo guarda en la List
            buyersDTO.add(modelMapper.map(buyer, BuyerDTORes.class));
        }
        return new PageImpl<>(buyersDTO, pageable, buyersDTO.size()) ;
    }

    //MODIFICA DATOS DEL COMPRADOR
    @Override
    public void updateBuyer(BuyerDTORes buyerDTO) throws Exception {
        var buyerBD = buyerRepository.findById(buyerDTO.getBuyerId())
                .orElseThrow(() -> new Exception("El id " + buyerDTO.getBuyerId() + " no existe"));
        //Si el dni ingresado ya existe en la BD lanza una Exception
        this.validateIfExistsByDni(buyerDTO.getDni(), buyerBD.getDni());
        buyerRepository.save( modelMapper.map(buyerBD, Buyer.class));
    }

    //ELIMINA UN COMPRADOR POR ID
    @Override
    public void deleteBuyer(Long buyerId) {
        buyerRepository.deleteById(buyerId);
    }

    // Métodos de validación

    public void validateIfExistsByDni(String BuyerDTODni, String buyerBDDni) throws Exception {
        if(buyerRepository.existsByDni(BuyerDTODni)
                && !BuyerDTODni.equals(buyerBDDni)){
            throw new Exception("El dni " + BuyerDTODni + " ya existe. Ingrese un nuevo dni");
        }
    }


}
