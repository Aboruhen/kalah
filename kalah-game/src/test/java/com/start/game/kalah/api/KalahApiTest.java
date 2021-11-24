package com.start.game.kalah.api;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


import com.start.game.kalah.api.model.GameInit;
import com.start.game.kalah.api.model.GameStatus;
import com.start.game.kalah.engine.Player;
import com.start.game.kalah.service.KalahGameService;
import java.net.URI;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(KalahApi.class)
@Disabled
class KalahApiTest {

    @MockBean
    private KalahGameService kalahGameService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void initBoard() throws Exception {
        URI uri = new URI("http://uri");
        Mockito.when(kalahGameService.initGame()).thenReturn(new GameInit("UUID", uri));

        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/kalah/games"))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(jsonPath("$.id").value("UUID"))
            .andExpect(jsonPath("$.uri").value(uri.toString()));
    }

    @Test
    void play() throws Exception {
        URI uri = new URI("http://uri");

        Mockito.when(kalahGameService.move(Player.ONE, "1", 1))
            .thenReturn(new GameStatus("1", uri, "formattedStatus"));

        this.mockMvc.perform(MockMvcRequestBuilders.put("/api/kalah/games/1/pits/1"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(jsonPath("$.id").value("1"))
            .andExpect(jsonPath("$.url").value(uri.toString()))
            .andExpect(jsonPath("$.status").value("formattedStatus"));
    }
}