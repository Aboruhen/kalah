package com.start.game.kalah.engine;

import java.util.List;
import java.util.stream.Stream;
import lombok.Getter;

@Getter
public enum Player {

    NONE(-1),
    ONE(6, 0, 1, 2, 3, 4, 5),
    TWO(13, 7, 8, 9, 10, 11, 12);

    private final List<Integer> pitIndexes;
    private final int houseIndex;

    Player(int houseIndex, Integer... pitIndexes) {
        this.pitIndexes = Stream.of(pitIndexes).toList();
        this.houseIndex = houseIndex;
    }

    public int getPitBoardIndex(int pitId) {
        return pitId - 1;
    }

}
