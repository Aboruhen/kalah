package com.start.game.kalah.api;

import com.start.game.kalah.api.model.GameInit;
import com.start.game.kalah.api.model.GameStatus;
import com.start.game.kalah.service.KalahGameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class KalahGameController {

    private final KalahGameService kalahGameService;

    @RequestMapping({"", "/", "/index"})
    public String getIndexPage(Model model) {
        return "init";
    }

    /**
     * Create new game.
     * @return new game URL
     */
    @PostMapping(value = "/games")
    public String createGame(Model model) {
        log.debug("Create new kalah game");
        GameInit body = kalahGameService.initGame();
        return "redirect:/games/" + body.id();
    }

    /**
     * Create new game.
     * @return new game URL
     */
    @GetMapping(value = "/games/{id}")
    public String board(@PathVariable String id, Model model) {
        log.debug("Create new kalah game");
        GameStatus game = kalahGameService.findGame(id);
        model.addAttribute("game", game);
        return "games";
    }

    /**
     * Player move.
     * @return current game status
     */
    @PostMapping("/games/{gameId}/pits/{pitId}")
    public String move(@PathVariable String gameId, @PathVariable int pitId, Model model) {
        GameStatus game = kalahGameService.move(gameId, pitId);
        return "redirect:/games/" + game.id();
    }


}
