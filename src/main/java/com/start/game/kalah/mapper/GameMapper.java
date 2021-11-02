package com.start.game.kalah.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.start.game.kalah.engine.KalahBoard;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GameMapper {

    private final ObjectMapper objectMapper;

    /**
     * Convert kalah board into a readable status.
     * Format of pit value: "1":"6". "1" is a pit id and "6" number of stone in a pit
     * @return formatted board view
     */
    public Object mapKalahStatus(KalahBoard board) {
        int[] pits = board.getPits();
        ObjectNode pitStatus = objectMapper.createObjectNode();
        IntStream.range(0, pits.length).boxed()
            .forEach(index -> pitStatus.put(String.valueOf(index + 1), String.valueOf(pits[index])));
        return pitStatus;
    }

}
