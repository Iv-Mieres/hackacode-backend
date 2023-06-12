package com.hackacode.themepark.service;

import com.hackacode.themepark.dto.request.GameDTOReq;
import com.hackacode.themepark.dto.request.GameDTOReq;
import com.hackacode.themepark.dto.response.GameDTORes;
import com.hackacode.themepark.dto.response.GameDTORes;
import com.hackacode.themepark.model.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IGameService {

    void saveGame(GameDTOReq gameDTO) throws Exception;
    GameDTORes getGameById(Long gameId) throws Exception;
    Page<GameDTORes> getAllGames(Pageable pageable);
    void updateGame(GameDTORes gameDTO) throws Exception;
    void deleteGame(Long gameID);

}
