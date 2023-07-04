package com.hackacode.themepark.service;

import com.hackacode.themepark.dto.request.GameDTOReq;
import com.hackacode.themepark.dto.response.GameDTORes;
import com.hackacode.themepark.dto.response.ReportDTORes;
import com.hackacode.themepark.exception.IdNotFoundException;
import com.hackacode.themepark.exception.NameExistsException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface IGameService {

    void saveGame(GameDTOReq gameDTO) throws NameExistsException;
    GameDTORes getGameById(Long gameId) throws IdNotFoundException;

    Page<GameDTORes> getAllGames(Pageable pageable);
    void updateGame(GameDTOReq gameDTO) throws IdNotFoundException, NameExistsException;
    void deleteGame(Long gameID);

}
