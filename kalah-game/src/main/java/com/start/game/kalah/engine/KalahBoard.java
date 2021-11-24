package com.start.game.kalah.engine;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Presentaion of the Kalah game Board.
 */
@RequiredArgsConstructor
@EqualsAndHashCode
@Getter
public class KalahBoard {

    private final int[] pits;
    private final Player lastMove;
    private final Player nextMove;

    public KalahBoard() {
        this(new int[] {6, 6, 6, 6, 6, 6, 0, 6, 6, 6, 6, 6, 6, 0});
    }

    /**
     * Initiate custom board. Primary used for tests
     * @param pits filled bu stones
     */
    KalahBoard(int[] pits) {
        this.pits = pits;
        this.lastMove = null;
        this.nextMove = null;
    }

}
