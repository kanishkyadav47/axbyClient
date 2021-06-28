package com.trial.axbyClient.Client.api;

import com.trial.axbyClient.Client.config.MoveConfig;
import com.trial.axbyClient.Client.dto.Board;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "jplaceholder1", url = "http://localhost:8080/", configuration = MoveConfig.class)
public interface MoveApi {

    //@RequestLine("POST /move/player/{playerId}/stacknum/{nStack}")
    @RequestMapping(method = RequestMethod.POST, value = "/move/player/{playerId}/stacknum/{nStack}")
    Board makeMove(@PathVariable("playerId") int id, @PathVariable("nStack") int nStack);


    //@RequestLine("GET /myturn/{playerId}")
    @RequestMapping(method = RequestMethod.GET, value = "/myturn/{playerId}")
    String myTurn(@PathVariable("playerId") int id);

    @RequestMapping(method = RequestMethod.GET, value = "/board")
    Board getBoard();

    @RequestMapping(method = RequestMethod.GET, value = "/bothplayers")
    boolean bothPlayersPresent();

    @RequestMapping(method = RequestMethod.POST, value = "/rematch")
    boolean rematch();

    @RequestMapping(method = RequestMethod.POST, value = "/endgame")
    void endGame();
}
