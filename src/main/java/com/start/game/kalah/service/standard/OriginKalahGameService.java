package com.start.game.kalah.service.standard;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.start.game.kalah.api.KalahApi;
import com.start.game.kalah.api.model.GameInit;
import com.start.game.kalah.api.model.GameStatus;
import com.start.game.kalah.engine.KalahBoard;
import com.start.game.kalah.engine.KalahEngine;
import com.start.game.kalah.engine.Player;
import com.start.game.kalah.mapper.GameMapper;
import com.start.game.kalah.repository.model.KalahRepository;
import com.start.game.kalah.service.KalahGameService;
import java.net.URI;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class OriginKalahGameService implements KalahGameService {

    private final KalahEngine kalahEngine;
    private final KalahRepository kalahRepository;
    private final GameMapper gameMapper;

    /**
     * Create (initiate) a new game.
     * @return new game info {@link GameInit}
     */
    @Override
    public GameInit initGame() {
        String gameId = UUID.randomUUID().toString();
        URI gameUri = WebMvcLinkBuilder.linkTo(methodOn(KalahApi.class).initBoard()).slash(gameId).toUri();
        log.info("created new game with id: {}", gameId);
        KalahBoard kalahBoard = kalahRepository.create(gameId, new KalahBoard());
        log.debug("Saved new game board");
        log.trace("New game (id:{}) board: {}", gameId, kalahBoard);
        return new GameInit(gameId, gameUri);
    }

    /**
     * Make a player move. Based on Kalah game engine
     * Note: should be added a check for a WIN case. But there are no requirements how to handle it
     * @return current game status {@link GameStatus}.
     */
    @Override
    public GameStatus move(Player player, String gameId, int pitId) {
        log.info("Player {} move {}", player, pitId);
        KalahBoard kalahBoard = kalahRepository.findById(gameId);
        if (kalahBoard.getNextMove() == Player.NONE) {
            throw new IllegalArgumentException("Game is already finished");
        }

        KalahBoard newBoard = kalahEngine.move(kalahBoard, player, pitId);

        KalahBoard savedGame = kalahRepository.save(gameId, newBoard);
        log.debug("Game (id:{}) board is changed", gameId);
        log.trace("Changed game (id:{}) board: {}", gameId, kalahBoard);
        URI gameUri = getGameUri(gameId);
        return new GameStatus(gameId, gameUri, gameMapper.mapKalahStatus(savedGame));
    }

    /**
     * Make a player move. Based on Kalah game engine
     * Note: should be added a check for a WIN case. But there are no requirements how to handle it
     * @return current game status {@link GameStatus}.
     */
    @Override
    public GameStatus move(String gameId, int pitId) {
        Player player = Player.NONE;
        if (Player.ONE.getPitIndexes().contains(pitId)) {
            player = Player.ONE;
        }
        if (Player.TWO.getPitIndexes().contains(pitId)) {
            player = Player.TWO;
        }
        if (player == Player.NONE) {
            throw new IllegalArgumentException("Incorrect pit index");
        }
        log.debug("Move is done by player: {} to pit {}, in game: {}", player, pitId, gameId);
        log.info("Player {} move {}", player, pitId);
        KalahBoard kalahBoard = kalahRepository.findById(gameId);
        if (kalahBoard.getNextMove() == Player.NONE) {
            throw new IllegalArgumentException("Game is already finished");
        }

        KalahBoard newBoard = kalahEngine.move(kalahBoard, player, pitId);

        KalahBoard savedGame = kalahRepository.save(gameId, newBoard);
        log.debug("Game (id:{}) board is changed", gameId);
        log.trace("Changed game (id:{}) board: {}", gameId, kalahBoard);
        URI gameUri = getGameUri(gameId);
        return new GameStatus(gameId, gameUri, savedGame);
    }

    @Override
    public GameStatus findGame(String gameId) {
        KalahBoard kalahBoard = kalahRepository.findById(gameId);
        URI gameUri = getGameUri(gameId);
        return new GameStatus(gameId, gameUri, kalahBoard.getPits());
    }

    private URI getGameUri(String gameId) {
        return WebMvcLinkBuilder.linkTo(methodOn(KalahApi.class).initBoard()).slash(gameId).toUri();
    }

}
