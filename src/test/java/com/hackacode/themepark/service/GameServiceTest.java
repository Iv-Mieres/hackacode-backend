package com.hackacode.themepark.service;

import com.hackacode.themepark.dto.request.EmployeeDTOReq;
import com.hackacode.themepark.dto.request.GameDTOReq;
import com.hackacode.themepark.dto.request.ScheduleDTOReq;
import com.hackacode.themepark.dto.response.GameDTORes;
import com.hackacode.themepark.dto.response.ScheduleDTORes;
import com.hackacode.themepark.model.Employee;
import com.hackacode.themepark.model.Game;
import com.hackacode.themepark.model.Role;
import com.hackacode.themepark.model.Schedule;
import com.hackacode.themepark.repository.IGameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    @InjectMocks
    private GameService gameService;
    @Mock
    private IGameRepository gameRepository;

    @Mock
    private ModelMapper modelMapper;

    private Game game;

    private GameDTOReq gameDTOReq;

    private GameDTORes gameDTORes;

    @BeforeEach
    void setUp() {

        this.game = new Game();
        this.gameDTOReq = new GameDTOReq();
        this.gameDTORes = new GameDTORes();
    }

    @Test
    void saveGame() throws Exception {
        var schedule = new ScheduleDTOReq();
        schedule.setId(1L);
        schedule.setStartTime(LocalTime.of(2,00));

        this.gameDTOReq.setSchedule(schedule);

        when(gameRepository.existsBySchedule_id(1L)).thenReturn(true);
        when(modelMapper.map(this.gameDTOReq, Game.class)).thenReturn(this.game);
        gameService.saveGame(this.gameDTOReq);
        verify(gameRepository).save(this.game);
    }

    @Test
    void ifExistsByIdThenThrowAnException() throws Exception {
        String name = "Montaña Rusa";
        this.gameDTOReq.setName(name);
        String espectedMjError = "El nombre " + name + " ya existe. Ingrese un nuevo nombre";

        when(gameRepository.existsByName(name)).thenReturn(true);
        Exception currentMjError = assertThrows(Exception.class,
                () -> gameService.saveGame(this.gameDTOReq));

        assertEquals(espectedMjError, currentMjError.getMessage());
    }

    @Test
    void getGameById() throws Exception {

        when(gameRepository.findById(1L)).thenReturn(Optional.ofNullable(this.game));
        when(modelMapper.map(this.game, GameDTORes.class)).thenReturn(this.gameDTORes);
        var gameDTOResult =  gameService.getGameById(1L);
        verify(gameRepository).findById(1L);

        assertEquals(this.gameDTORes, gameDTOResult);
    }

//    @Test
//    void getAllGamesPageable(){
//
//        int page = 0;
//        int size = 2;
//
//        var schedule = new Schedule();
//        schedule.setId(4L);
//
//        var game1 = Game.builder()
//                .id(1L)
//                .price(3000.0)
//                .requiredAge(18)
//                .schedule(schedule)
//                .build();
//        var game2 = Game.builder().id(2L).build();
//
//        this.gameDTORes.setId(1L);
//        this.gameDTORes.setPrice(3000.0);
//        this.gameDTORes.setRequiredAge(18);
//        this.gameDTORes.setSchedule(modelMapper.map(schedule, ScheduleDTORes.class));
//
//        var games = new ArrayList<Game>();
//        games.add(game1);
//        games.add(game2);
//
//        var pageable = PageRequest.of(page, size);
//        when(gameRepository.findAll(pageable)).thenReturn(new PageImpl<>(games,pageable, games.size()));
//        when(modelMapper.map(game1, GameDTORes.class)).thenReturn(this.gameDTORes);
//        var result = gameService.getAllGames(pageable);
//
//        assertEquals(this.gameDTORes, result.getContent().get(0));
//
//        assertEquals(games.size(), result.getTotalElements());
//        assertEquals(0, result.getNumber());
//        assertEquals(1, result.getTotalPages());
//
//    }
//
//    @Test
//    void updateGame() throws Exception {
//
//        this.game.setId(1L);
//        var schedule = new Schedule();
//        schedule.setId(4L);
//
//        this.gameDTOReq.setId(1L);
//        this.gameDTOReq.setName("Montaña Rusa");
//        this.gameDTOReq.setPrice(3000.0);
//        this.gameDTOReq.setRequiredAge(18);
//        this.gameDTOReq.setSchedule(modelMapper.map(schedule, ScheduleDTOReq.class));
//
//        when(gameRepository.findById(1L)).thenReturn(Optional.ofNullable(this.game));
//        when(gameRepository.existsByName(this.gameDTOReq.getName())).thenReturn(false);
//        when(modelMapper.map(this.gameDTOReq, Game.class)).thenReturn(this.game);
//        gameService.updateGame(this.gameDTOReq);
//        verify(gameRepository).save(this.game);
//    }

    @Test
    void ifWhenUpdatingAGameTheNameExistsThrowAnException() throws Exception {

        String name = "Montaña Rusa";
        this.gameDTOReq.setId(1L);
        this.gameDTOReq.setName(name);
        this.game.setId(1L);
        this.game.setName("Calesita");
        String espectedMjError = "El nombre " + this.gameDTOReq.getName() + " ya existe. Ingrese un nuevo nombre";

        when(gameRepository.findById(this.gameDTOReq.getId())).thenReturn(Optional.ofNullable(this.game));
        when(gameRepository.existsByName(this.gameDTOReq.getName())).thenReturn(true);

        Exception currentMjError = assertThrows(Exception.class,
                () -> gameService.updateGame(this.gameDTOReq));

        assertEquals(espectedMjError, currentMjError.getMessage());
    }

    @Test
    void deleteGameById(){
        doNothing().when(gameRepository).deleteById(1L);
        gameService.deleteGame(1L);
        verify(gameRepository).deleteById(1L);
    }
}