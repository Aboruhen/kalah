package com.start.game.kalah.service;

import com.start.game.kalah.api.model.GameInit;
import com.start.game.kalah.api.model.GameStatus;
import com.start.game.kalah.engine.Player;

public interface KalahGameService {

    GameInit initGame();

    GameStatus move(Player player, String gameId, int pitId);

}
