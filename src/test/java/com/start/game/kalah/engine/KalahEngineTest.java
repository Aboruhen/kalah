package com.start.game.kalah.engine;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@Disabled
class KalahEngineTest {


    @InjectMocks
    private KalahEngine kalahEngine;

    @Test
    void firstPlayerInitMove() {
        KalahBoard kalahBoard = new KalahBoard();

        KalahBoard newBoard = kalahEngine.move(kalahBoard, Player.ONE, 1);

        Assertions.assertEquals(Player.ONE, newBoard.getLastMove());
        Assertions.assertEquals(Player.ONE, newBoard.getNextMove());
        Assertions.assertArrayEquals(new int[] {0, 7, 7, 7, 7, 7, 1, 6, 6, 6, 6, 6, 6, 0}, newBoard.getPits());
    }

    @Test
    void secondPlayerInitMove() {
        KalahBoard kalahBoard = new KalahBoard();

        KalahBoard newBoard = kalahEngine.move(kalahBoard, Player.TWO, 8);

        Assertions.assertEquals(Player.TWO, newBoard.getLastMove());
        Assertions.assertEquals(Player.TWO, newBoard.getNextMove());
        Assertions.assertArrayEquals(new int[] {6, 6, 6, 6, 6, 6, 0, 0, 7, 7, 7, 7, 7, 1}, newBoard.getPits());
    }

    @Test
    void firstPlayerMoveLastStoneIntoEmptyPit() {
        KalahBoard kalahBoard = new KalahBoard(new int[] {4, 3, 3, 3, 0, 5, 5, 3, 3, 3, 3, 5, 3, 2});

        KalahBoard newBoard = kalahEngine.move(kalahBoard, Player.ONE, 1);

        Assertions.assertEquals(Player.ONE, newBoard.getLastMove());
        Assertions.assertEquals(Player.TWO, newBoard.getNextMove());
        Assertions.assertArrayEquals(new int[] {0, 4, 4, 4, 0, 5, 11, 3, 3, 3, 3, 0, 3, 2}, newBoard.getPits());
    }

    @Test
    void secondPlayerMoveLastStoneIntoEmptyPit() {
        KalahBoard kalahBoard = new KalahBoard(new int[] {3, 3, 3, 3, 5, 3, 2, 4, 3, 3, 3, 0, 5, 5});

        KalahBoard newBoard = kalahEngine.move(kalahBoard, Player.TWO, 8);

        Assertions.assertEquals(Player.TWO, newBoard.getLastMove());
        Assertions.assertEquals(Player.ONE, newBoard.getNextMove());
        Assertions.assertArrayEquals(new int[] {3, 3, 3, 3, 0, 3, 2, 0, 4, 4, 4, 0, 5, 11}, newBoard.getPits());
    }

    @Test
    void firstPlayerMoveOverOpponentKalah() {
        KalahBoard kalahBoard = new KalahBoard(new int[] {1, 1, 1, 1, 1, 9, 1, 1, 1, 1, 1, 1, 1, 1});

        KalahBoard newBoard = kalahEngine.move(kalahBoard, Player.ONE, 6);

        Assertions.assertEquals(Player.ONE, newBoard.getLastMove());
        Assertions.assertEquals(Player.TWO, newBoard.getNextMove());
        Assertions.assertArrayEquals(new int[] {2, 2, 1, 1, 1, 0, 2, 2, 2, 2, 2, 2, 2, 1}, newBoard.getPits());
    }

    @Test
    void firstPlayerEndGame() {
        KalahBoard kalahBoard = new KalahBoard(new int[] {0, 0, 0, 0, 0, 1, 9, 1, 1, 1, 1, 1, 1, 0});

        KalahBoard newBoard = kalahEngine.move(kalahBoard, Player.ONE, 6);

        Assertions.assertEquals(Player.ONE, newBoard.getLastMove());
        Assertions.assertEquals(Player.NONE, newBoard.getNextMove());
        Assertions.assertArrayEquals(new int[] {0, 0, 0, 0, 0, 0, 10, 0, 0, 0, 0, 0, 0, 6}, newBoard.getPits());
    }

    @Test
    void secondPlayerEndGame() {
        KalahBoard kalahBoard = new KalahBoard(new int[] {1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 9});

        KalahBoard newBoard = kalahEngine.move(kalahBoard, Player.TWO, 13);

        Assertions.assertEquals(Player.TWO, newBoard.getLastMove());
        Assertions.assertEquals(Player.NONE, newBoard.getNextMove());
        Assertions.assertArrayEquals(new int[] {0, 0, 0, 0, 0, 0, 6, 0, 0, 0, 0, 0, 0, 10}, newBoard.getPits());
    }

}