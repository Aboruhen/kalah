package com.start.game.kalah.api;

import com.start.game.kalah.api.model.GameInit;
import com.start.game.kalah.api.model.GameStatus;
import com.start.game.kalah.engine.Player;
import com.start.game.kalah.service.KalahGameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/kalah")
public class KalahApi {

    private final KalahGameService kalahGameService;

    /**
     * Create new game.
     * @return new game URL
     */
    @PostMapping(value = "/games")
    public ResponseEntity<GameInit> initBoard() {
        log.debug("Create new kalah game");
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(kalahGameService.initGame());
    }

    /**
     * Player move.
     * @return current game status
     */
    @PutMapping("/games/{gameId}/pits/{pitId}")
    public ResponseEntity<GameStatus> play(@PathVariable String gameId, @PathVariable Integer pitId) {
        log.debug("Next Move from pit {}, in game: {}", pitId, gameId);

        return ResponseEntity.ok().body(gameMove(gameId, pitId));
    }

    private GameStatus gameMove(String gameId, Integer pitId) {
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
        return kalahGameService.move(player, gameId, pitId);
    }

}
