package com.hackacode.themepark.service;

import com.hackacode.themepark.dto.request.GameDTOReq;
import com.hackacode.themepark.dto.response.GameDTORes;
import com.hackacode.themepark.dto.response.ReportDTORes;
import com.hackacode.themepark.exception.IdNotFoundException;
import com.hackacode.themepark.exception.NameExistsException;
import com.hackacode.themepark.model.Game;
import com.hackacode.themepark.model.Sale;
import com.hackacode.themepark.model.Ticket;
import com.hackacode.themepark.repository.IGameRepository;
import com.hackacode.themepark.repository.ISaleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

@Service
public class GameService implements IGameService{

    @Autowired
    private IGameRepository gameRepository;
    @Autowired
    private ISaleRepository saleRepository;
    @Autowired
    private IWordsConverter wordsConverter;
    @Autowired
    private ModelMapper modelMapper;

//    CREA UN JUEGO
    @Override
    public void saveGame(GameDTOReq gameDTO) throws NameExistsException {
        if (gameRepository.existsByName(gameDTO.getName())) {
            throw new NameExistsException("El nombre " + gameDTO.getName() + " ya existe. Ingrese un nuevo nombre");
        }
        //convierte la primer letra de cada palabra en mayúscula
        gameDTO.setName(wordsConverter.capitalizeWords(gameDTO.getName()));

        gameRepository.save(modelMapper.map(gameDTO, Game.class));
    }

    //MUESTRA UN JUEGO POR ID
    @Override
    public GameDTORes getGameById(Long gameId) throws IdNotFoundException {
        return modelMapper.map(gameRepository.findById(gameId)
                .orElseThrow(() -> new IdNotFoundException("El id " + gameId + " no exite. Ingrese un nuevo id")), GameDTORes.class);
    }

    //LISTA JUEGOS PAGINADOS
    @Override
    public Page<GameDTORes> getAllGames(Pageable pageable) {
        var gamesBD = gameRepository.findAll(pageable);
        var gamesDTO = new ArrayList<GameDTORes>();
        //recorre la lista de juegos de la BD, los convierte a DTO y los guarda en una listaDTO
        for (Game game : gamesBD) {
            gamesDTO.add(modelMapper.map(game, GameDTORes.class));
        }
        return new PageImpl<>(gamesDTO, pageable, gamesDTO.size());
    }

    //ACTUALIZA UN JUEGO
    @Override
    public void updateGame(GameDTOReq gameDTO) throws IdNotFoundException, NameExistsException {
        var gameBD = gameRepository.findById(gameDTO.getId())
                .orElseThrow(() -> new IdNotFoundException("El id " + gameDTO + " no existe. Ingrese un nuevo id"));
        //valida que el nombre del juego no exista y si existe que coincida con el juego encontrado
        if (!gameDTO.getName().equals(gameBD.getName()) && gameRepository.existsByName(gameDTO.getName())) {
            throw new NameExistsException("El nombre " + gameDTO.getName() + " ya existe. Ingrese un nuevo nombre");
        }
        //convierte la primer letra de cada palabra en mayúscula
        gameDTO.setName(wordsConverter.capitalizeWords(gameDTO.getName()));
        gameRepository.save(modelMapper.map(gameDTO, Game.class));
    }

    //ELIMINA UN JUEGO
    @Override
    public void deleteGame(Long gameID) {
        gameRepository.deleteById(gameID);
    }


    //MUESTRA JUEGO CON LA MAYOR CANTIDAD DE TICKETS VENDIDOS EN DETERMINADO DIA
    @Override
    public ReportDTORes gameWithTheMostTicketsSold(LocalDate date){
        LocalDateTime dateTime = LocalDateTime.of(date, LocalTime.MAX);
        Sale saleDB = saleRepository.findTopByPurchaseDateBeforeOrderByGame_idDesc(dateTime);

        Long totalTicketsSold = saleRepository.countSalesByGame_Id(saleDB.getGame().getId());
        String gameName = saleDB.getGame().getName();
        return ReportDTORes.builder()
                .totalTicketsSold(totalTicketsSold)
                .gameName(gameName)
                .build();
    }

//        public ReportDTORes oneGameWithTheMostTicketsSold(LocalDateTime date){
//
//        var games = gameRepository.findAll();
//        String gameName = null;
//        long totalNormalTicketsSold = 0L;
//
//        //recorre la lista de juegos
//        for (Game game : games) {
//            int totalTickets = 0;
//            //recorre la lista de tickets normal del juego y comprueba que la
//            //fecha de los tickets sea anterior o igual a la ingresada y lo guarda en un contador
//            for (Ticket ticket : game.getTickets()) {
//                if (ticket.getPurchaseDate().isBefore(date) || ticket.getPurchaseDate().equals(date)) {
//                    totalTickets++;
//                }
//            }
//            //compara si el contador del siguiente juego es mayor a la cantidad de tickets guardada
//            if (totalTickets > totalNormalTicketsSold) {
//                totalNormalTicketsSold= totalTickets;
//                gameName = game.getName();
//            }
//        }
//        return ReportDTORes.builder()
//                .totalNormalTicketsSold(totalNormalTicketsSold)
//                .gameName(gameName)
//                .build();
//    }


}
