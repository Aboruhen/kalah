package com.start.game.kalah.repository.model;

import com.start.game.kalah.engine.KalahBoard;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Simple in-memory repository implementation.
 */
@Slf4j
@Component
public class KalahRepository {

    private static final Map<String, KalahBoard> gameMap = new ConcurrentHashMap<>();

    /**
     * This method create new Game and save the object in a Map.
     *
     * @return KalahBoard {@link KalahBoard}.
     */
    public KalahBoard create(String gameId, KalahBoard kalahBoard) {
        gameMap.put(gameId, kalahBoard);
        return gameMap.get(gameId);
    }

    /**
     * This method save/update object in a Map.
     *
     * @return KalahBoard {@link KalahBoard}.
     */
    public KalahBoard save(String gameId, KalahBoard kalahBoard) {
        gameMap.put(gameId, kalahBoard);
        return gameMap.get(gameId);
    }

    /**
     * This method will return the game object by id.
     *
     * @param gameId is the game id.
     * @return Game
     */
    public KalahBoard findById(String gameId) {
        KalahBoard game = gameMap.get(gameId);
        if (game == null) {
            throw new IllegalArgumentException("Game is not found for the id: " + gameId);
        }
        return game;
    }

}
