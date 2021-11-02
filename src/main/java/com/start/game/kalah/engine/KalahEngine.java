package com.start.game.kalah.engine;

import java.util.List;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * This is an implementations Kalah game.
 * <a href="https://en.wikipedia.org/wiki/Kalah">Kalah Game Wiki</a>.
 * The player who begins picks up all the stones in any of their own pits,
 * and sows the stones on to the right, one in each of the following pits,
 * including his own Kalah. No stones are put in the opponent's' Kalah.
 * If the players last stone lands in his own Kalah, he gets another turn.
 * This can be repeated any number of times before it's the other player's turn.
 * When the last stone lands in an own empty pit,
 * the player captures this stone and all stones in the opposite pit (the other players' pit)
 * and puts them in his own Kalah.
 * The game is over as soon as one of the sides run out of stones.
 * The player who still has stones in his/her pits keeps them and puts them in his/hers Kalah.
 * The winner of the game is the player who has the most stones in his Kalah
 */

@Slf4j
@Component
public class KalahEngine {

    public static final int PLAYER_TRAY_NUMBER = 7;

    /**
     * Make a player move. Sows stones to next pits.
     * @param kalahBoard current game board
     * @param player who did a move
     * @param pitId player pit id
     * @return new changed board {@link KalahBoard}
     */
    public KalahBoard move(KalahBoard kalahBoard, Player player, int pitId) {
        int pitBoardIndex = player.getPitBoardIndex(pitId);
        log.debug("Find exact array player pit board index {}", pitBoardIndex);
        return stoneSow(kalahBoard, pitBoardIndex, player);
    }

    private KalahBoard stoneSow(KalahBoard kalahBoard, int pitBoardIndex, Player player) {
        int[] pits = kalahBoard.getPits();
        int pitStoneCount = pits[pitBoardIndex];
        pits[pitBoardIndex] = 0;
        int lastPitIndex = fillNextPits(pits, pitBoardIndex, player, pitStoneCount);
        grepOpponentStones(pits, player, lastPitIndex);
        //The game is over as soon as one of the sides run out of stones.
        if (isGameFinish(player, pits)) {
            //The player who still has stones in his/her pits keeps them and puts them in his/hers Kalah.
            collectLeftStones(player, pits);
            collectLeftStones(getOpponent(player), pits);
            return new KalahBoard(pits, player, Player.NONE);
        }

        //If the players last stone lands in his own Kalah, he gets another turn.
        if (lastPitIndex == player.getHouseIndex()) {
            return new KalahBoard(pits, player, player);
        }
        return new KalahBoard(pits, player, getOpponent(player));
    }

    private void collectLeftStones(Player player, int[] pits) {
        player.getPitIndexes().forEach(index -> {
            pits[player.getHouseIndex()] += pits[index];
            pits[index] = 0;
        });
    }

    private boolean isGameFinish(Player player, int[] pits) {
        return player.getPitIndexes().stream().allMatch(index -> pits[index] == 0);
    }

    /**
     * When the last stone lands in an own empty pit
     * The player captures this stone and all stones in the opposite pit (the other players' pit).
     */
    private void grepOpponentStones(int[] pits, Player player, int lastPitIndex) {
        if (lastPitIndex != player.getHouseIndex() && pits[lastPitIndex] == 1) {
            pits[player.getHouseIndex()] += pits[lastPitIndex];
            int opponentPit = findOpponentOppositePit(lastPitIndex);
            pits[player.getHouseIndex()] += pits[opponentPit];
            pits[lastPitIndex] = 0;
            pits[opponentPit] = 0;
        }
    }

    private int findOpponentOppositePit(int lastPitIndex) {
        if (lastPitIndex >= PLAYER_TRAY_NUMBER) {
            return lastPitIndex - PLAYER_TRAY_NUMBER;
        }
        return lastPitIndex + PLAYER_TRAY_NUMBER;
    }

    private int fillNextPits(int[] pits, int pitBoardIndex, Player player, int pitStoneCount) {
        int nexPitIndex = pitBoardIndex;
        while (pitStoneCount > 0) {
            nexPitIndex = nextPitsIndex(nexPitIndex, player);
            pits[nexPitIndex]++;
            pitStoneCount--;
        }
        return nexPitIndex;
    }

    private int nextPitsIndex(int pitBoardIndex, Player player) {
        int nexPitIndex = pitBoardIndex + 1;
        if (player.getPitIndexes().contains(nexPitIndex)) {
            return nexPitIndex;
        }
        if (nexPitIndex == getOpponent(player).getHouseIndex()) {
            return 0;
        }
        return nexPitIndex;
    }

    private Player getOpponent(Player player) {
        return player == Player.ONE ? Player.TWO : Player.ONE;
    }

}
