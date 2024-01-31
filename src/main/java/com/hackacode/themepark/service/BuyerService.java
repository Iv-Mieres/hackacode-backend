package com.hackacode.themepark.service;

import com.hackacode.themepark.dto.request.BuyerDTOReq;
import com.hackacode.themepark.dto.response.BuyerDTORes;
import com.hackacode.themepark.exception.DniExistsException;
import com.hackacode.themepark.exception.IdNotFoundException;
import com.hackacode.themepark.model.Buyer;
import com.hackacode.themepark.repository.IBuyerRepository;
import com.hackacode.themepark.util.IWordsConverter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

@Service
public class BuyerService implements IBuyerService {

    @Autowired
    private IBuyerRepository buyerRepository;

    @Autowired
    private ITicketDetailService ticketDetailService;

    @Autowired
    private IWordsConverter wordsConverter;

    @Autowired
    private ModelMapper modelMapper;

    //CREA UN COMPRADOR
    @Override
    public void saveBuyer(BuyerDTOReq buyerDTO) throws DniExistsException {
        //Convierte a mayúscula la primera letra de cada pabra ingresada
        buyerDTO.setName(wordsConverter.capitalizeWords(buyerDTO.getName()));
        buyerDTO.setSurname(wordsConverter.capitalizeWords(buyerDTO.getSurname()));

        //Comprueba que el dni sea unico
        if (buyerRepository.existsByDni(buyerDTO.getDni())) {
            throw new DniExistsException("El dni " + buyerDTO.getDni() + " ya existe. Ingrese un nuevo dni");
        }
        buyerRepository.save(modelMapper.map(buyerDTO, Buyer.class));
    }

    //BUSCA COMPRADOR POR ID
    @Override
    public BuyerDTORes getBuyerById(Long buyerId) throws IdNotFoundException {
        var buyerDTO = modelMapper.map(buyerRepository.findById(buyerId)
                .orElseThrow(() -> new IdNotFoundException("El id " + buyerId + " no existe")), BuyerDTORes.class);
        buyerDTO.setLastVisit(ticketDetailService.lastVisit(buyerId));
        buyerDTO.setAge(Period.between(buyerDTO.getBirthdate(), LocalDate.now()).getYears());
        return buyerDTO;
    }

    //LISTA DTO DE COMPRADORES PAGINADOS
    @Override
    public Page<BuyerDTORes> getAllBuyers(Pageable pageable) {
        //recorre la lista de compradores, los convierte a DTO y los guarda en la List buyersDTO
        var buyersDTO = new ArrayList<BuyerDTORes>();

        //convierte el comprador a DTO y lo guarda en la lista
        for (Buyer buyer : buyerRepository.findAll(pageable)) {
                var buyerDTO = modelMapper.map(buyer, BuyerDTORes.class);
                buyerDTO.setAge(Period.between(buyerDTO.getBirthdate(), LocalDate.now()).getYears());
                buyerDTO.setLastVisit(ticketDetailService.lastVisit(buyer.getId()));
                buyersDTO.add(buyerDTO);
        }
        return new PageImpl<>(buyersDTO, pageable, buyersDTO.size());
    }

    //MODIFICA DATOS DEL COMPRADOR
    @Override
    public void updateBuyer(BuyerDTOReq buyerDTO) throws IdNotFoundException, DniExistsException {
        var buyerBD = buyerRepository.findById(buyerDTO.getId())
                .orElseThrow(() -> new IdNotFoundException("El id " + buyerDTO.getId() + " no existe"));

        //Si el dni ingresado ya existe en la BD y no pertenece al comprador ingresado lanza una Exception
        this.validateIfExistsByDni(buyerDTO.getDni(), buyerBD.getDni());

        buyerDTO.setName(wordsConverter.capitalizeWords(buyerDTO.getName()));
        buyerDTO.setSurname(wordsConverter.capitalizeWords(buyerDTO.getSurname()));

        var saveBuyer = modelMapper.map(buyerDTO, Buyer.class);
        saveBuyer.setBanned(false);
        buyerRepository.save(saveBuyer);
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

    public void validateIfExistsByDni(String BuyerDTODni, String buyerBDDni) throws DniExistsException {
        if (!BuyerDTODni.equals(buyerBDDni) && buyerRepository.existsByDni(BuyerDTODni)) {
            throw new DniExistsException("El dni " + BuyerDTODni + " ya existe. Ingrese un nuevo dni");
        }
    }


}
