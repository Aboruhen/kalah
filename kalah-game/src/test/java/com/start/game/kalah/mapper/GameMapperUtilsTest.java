package com.start.game.kalah.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.start.game.kalah.engine.KalahBoard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GameMapperUtilsTest {

    @Test
    void mapKalahStatus() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Object jsonObject = new GameMapper(objectMapper).mapKalahStatus(new KalahBoard());
        String expected =
            "{\"1\":\"6\",\"2\":\"6\",\"3\":\"6\",\"4\":\"6\",\"5\":\"6\",\"6\":\"6\",\"7\":\"0\",\"8\":\"6\",\"9\":\"6\",\"10\":\"6\",\"11\":\"6\",\"12\":\"6\",\"13\":\"6\",\"14\":\"0\"}";
        Assertions.assertEquals(expected, objectMapper.writeValueAsString(jsonObject));
    }

}