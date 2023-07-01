package com.hackacode.themepark.service;

import com.hackacode.themepark.dto.request.EmployeeDTOReq;
import com.hackacode.themepark.dto.request.GameDTOReq;
import com.hackacode.themepark.dto.request.ScheduleDTOReq;
import com.hackacode.themepark.dto.response.GameDTORes;
import com.hackacode.themepark.dto.response.ScheduleDTORes;
import com.hackacode.themepark.exception.NameExistsException;
import com.hackacode.themepark.model.Employee;
import com.hackacode.themepark.model.Game;
import com.hackacode.themepark.model.Role;
import com.hackacode.themepark.model.Schedule;
import com.hackacode.themepark.repository.IGameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
    private IWordsConverter wordsConverter;
    @Mock
    private ModelMapper modelMapper;

    private Game game;

    private GameDTOReq gameDTOReq;

    private GameDTORes gameDTORes;

    @BeforeEach
    void setUp() {
        var schedule = new ScheduleDTOReq();
        schedule.setId(1L);
        schedule.setStartTime(LocalTime.of(8,00));
        schedule.setEndTime(LocalTime.of(14,30));

        this.game = Game.builder()
                .id(1L)
                .name("montaña rusa")
                .requiredAge(18).build();

        this.gameDTOReq = new GameDTOReq();
        this.gameDTOReq.setName("montaña rusa");
        this.gameDTOReq.setId(1L);
        this.gameDTOReq.setRequiredAge(18);
        this.gameDTOReq.setSchedule(schedule);

        this.gameDTORes = new GameDTORes();
    }

    @DisplayName("comprueba que se guarde un juego con su horario")
    @Test
    void saveGame() throws Exception {

        when(modelMapper.map(this.gameDTOReq, Game.class)).thenReturn(this.game);
        when(wordsConverter.capitalizeWords(this.game.getName())).thenReturn("Montaña Rusa");

        gameService.saveGame(this.gameDTOReq);

        assertEquals( "Montaña Rusa", this.gameDTOReq.getName());
        verify(gameRepository).save(this.game);
    }

    @DisplayName("comprueba que no se guarde un juego si el nombre ya existe")
    @Test
    void ifExistsByIdThenThrowAnException() throws NameExistsException {
        String name = "Montaña Rusa";
        this.gameDTOReq.setName(name);
        String expected = "El nombre " + name + " ya existe. Ingrese un nuevo nombre";

        when(gameRepository.existsByName(name)).thenReturn(true);
        Exception currentError = assertThrows(NameExistsException.class,
                () -> gameService.saveGame(this.gameDTOReq));

        assertEquals(expected, currentError.getMessage());
    }

    @DisplayName("comprueba que muestre un juego por su id")
    @Test
    void getGameById() throws Exception {

        when(gameRepository.findById(1L)).thenReturn(Optional.ofNullable(this.game));
        when(modelMapper.map(this.game, GameDTORes.class)).thenReturn(this.gameDTORes);
        var gameDTOResult =  gameService.getGameById(1L);
        verify(gameRepository).findById(1L);

        assertEquals(this.gameDTORes, gameDTOResult);
    }

    @DisplayName("comprueba que se muestre una lista de juegos paginada")
    @Test
    void getAllGamesPageable(){

        int page = 0;
        int size = 2;

        var schedule = new Schedule();
        schedule.setId(4L);

        var game1 = Game.builder()
                .id(1L)
                .requiredAge(18)
                .schedule(schedule)
                .build();
        var game2 = Game.builder().id(2L).build();

        this.gameDTORes.setId(1L);
        this.gameDTORes.setRequiredAge(18);
        this.gameDTORes.setSchedule(modelMapper.map(schedule, ScheduleDTORes.class));

        var games = new ArrayList<Game>();
        games.add(game1);
        games.add(game2);

        var pageable = PageRequest.of(page, size);
        when(gameRepository.findAll(pageable)).thenReturn(new PageImpl<>(games,pageable, games.size()));
        when(modelMapper.map(game1, GameDTORes.class)).thenReturn(this.gameDTORes);
        var result = gameService.getAllGames(pageable);

        assertEquals(this.gameDTORes, result.getContent().get(0));

        assertEquals(games.size(), result.getTotalElements());
        assertEquals(0, result.getNumber());
        assertEquals(1, result.getTotalPages());

    }

    @DisplayName("comprueba que se actualice un juego")
    @Test
    void updateGame() throws Exception {

        this.game.setId(1L);

        when(gameRepository.findById(1L)).thenReturn(Optional.ofNullable(this.game));
        when(wordsConverter.capitalizeWords(this.gameDTOReq.getName())).thenReturn("Montaña Rusa");
        when(modelMapper.map(this.gameDTOReq, Game.class)).thenReturn(this.game);

        gameService.updateGame(this.gameDTOReq);
        assertEquals("Montaña Rusa", this.gameDTOReq.getName());
        verify(gameRepository).save(this.game);
    }

    @DisplayName("comprueba que no se guarda el juego si el nombre ya existe y no pertenece al juego ingresado")
    @Test
    void ifWhenUpdatingAGameTheNameExistsThrowAnException() throws Exception {
        this.game.setName("Zamba");
        String expected = "El nombre " + this.gameDTOReq.getName() + " ya existe. Ingrese un nuevo nombre";

        when(gameRepository.findById(this.gameDTOReq.getId())).thenReturn(Optional.ofNullable(this.game));
        when(gameRepository.existsByName(this.gameDTOReq.getName())).thenReturn(true);

        Exception currentError = assertThrows(Exception.class, () -> gameService.updateGame(this.gameDTOReq));

        assertEquals(expected, currentError.getMessage());
    }

    @DisplayName("comprueba que se elimine un juego por id")
    @Test
    void deleteGameById(){
        doNothing().when(gameRepository).deleteById(1L);
        gameService.deleteGame(1L);
        verify(gameRepository).deleteById(1L);
    }
}