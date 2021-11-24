package com.start.game.kalah.service.standard;

import com.start.game.kalah.api.model.GameInit;
import com.start.game.kalah.api.model.GameStatus;
import com.start.game.kalah.engine.KalahBoard;
import com.start.game.kalah.engine.KalahEngine;
import com.start.game.kalah.engine.Player;
import com.start.game.kalah.mapper.GameMapper;
import com.start.game.kalah.repository.model.KalahRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@Disabled
class OriginKalahGameServiceTest {

    @Mock
    KalahRepository kalahRepository;
    @Mock
    GameMapper gameMapper;
    @Mock
    KalahEngine kalahEngine;
    @InjectMocks
    OriginKalahGameService originKalahGameService;

    @Test
    void initGame() {
        GameInit gameInit = originKalahGameService.initGame();

        Assertions.assertNotNull(gameInit.id());
        Assertions.assertNotNull(gameInit.uri());
        Mockito.verify(kalahRepository).create(gameInit.id(), new KalahBoard());
        Mockito.verify(kalahEngine, Mockito.never()).move(Mockito.any(), Mockito.any(), Mockito.anyInt());
    }

    @Test
    void move() {
        KalahBoard expectedBoard =
            new KalahBoard(new int[] {6, 6, 6, 6, 6, 6, 0, 0, 7, 7, 7, 7, 7, 1}, Player.ONE, Player.ONE);
        String expectedStatus =
            "\"1\":\"6\",\"2\":\"6\",\"3\":\"6\",\"4\":\"6\",\"5\":\"6\",\"6\":\"6\",\"7\":\"0\",\"8\":\"0\",\"9\":\"7\",\"10\":\"7\",\"11\":\"7\",\"12\":\"7\",\"13\":\"7\",\"14\":\"1\"";

        KalahBoard kalahBoard = new KalahBoard();
        Mockito.when(kalahRepository.findById("1")).thenReturn(kalahBoard);
        Mockito.when(kalahRepository.save("1", expectedBoard)).thenReturn(expectedBoard);
        Mockito.when(kalahEngine.move(kalahBoard, Player.ONE, 1))
            .thenReturn(expectedBoard);
        Mockito.when(gameMapper.mapKalahStatus(expectedBoard)).thenReturn(expectedStatus);

        GameStatus move = originKalahGameService.move(Player.ONE, "1", 1);

        Assertions.assertEquals(expectedStatus, move.status());
        Assertions.assertNotNull(move.id());
        Assertions.assertNotNull(move.url());
        Mockito.verify(kalahRepository).save("1", expectedBoard);

    }

}