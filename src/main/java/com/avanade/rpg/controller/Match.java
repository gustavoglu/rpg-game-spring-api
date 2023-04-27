package com.avanade.rpg.controller;

import com.avanade.rpg.model.TurnLog;
import com.avanade.rpg.repository.TurnLogRepository;
import com.avanade.rpg.requests.MatchNewGameRequest;
import com.avanade.rpg.responses.MatchTurnResponse;
import com.avanade.rpg.service.MatchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( value = "api/v1/match" )
@Api( value = "MATCHs API REST" )
@CrossOrigin( origins = "*" )
public class Match {

    @Autowired
    private MatchService service;
    @Autowired
    private TurnLogRepository turnLogRepository;

    @DeleteMapping( "/" )
    @ApiOperation( "Delete a character on character list" )
    public ResponseEntity< HttpStatus > delete( @RequestHeader Long id ) {
        service.delete( id );
        return new ResponseEntity<>( HttpStatus.NO_CONTENT );
    }

    @GetMapping( "/" )
    @ApiOperation( "find a characters in character list" )
    public ResponseEntity< List< com.avanade.rpg.model.Match > > getAll( ) {
        return new ResponseEntity<>( service.findAll( ), HttpStatus.OK );
    }

    @GetMapping( "/{id}" )
    @ApiOperation( "" )
    public ResponseEntity<com.avanade.rpg.model.Match> findById(@PathVariable( value = "id" ) Long matchId ) {
        var match = service.findById(matchId);
        return new ResponseEntity<>( match, HttpStatus.OK );
    }


    @PostMapping( "/new-game" )
    @ApiOperation( "" )
    public ResponseEntity<com.avanade.rpg.model.Match> newGame(@RequestBody MatchNewGameRequest request){


        var match = service.newGame(request.getPlayer1Name(),request.getCharacter1Id(),
                                request.getPlayer2Name(),request.getCharacter2Id());
        return new ResponseEntity<>( match, HttpStatus.OK );
    }

    @PostMapping( "/{id}/attack" )
    @ApiOperation( "" )
    public ResponseEntity<MatchTurnResponse> attack(@PathVariable( value = "id" ) Long matchId ) {
            var matchTurnResponse = service.attackTurn(matchId);
    return new ResponseEntity<>( matchTurnResponse, HttpStatus.OK );
    }

    @PostMapping( "/{id}/defense" )
    @ApiOperation( "" )
    public ResponseEntity< MatchTurnResponse > defense( @PathVariable( value = "id" ) Long matchId) {
        var matchTurnResponse = service.defenseTurn(matchId);
        return new ResponseEntity<>( matchTurnResponse , HttpStatus.CREATED );
    }

    @PostMapping( "/{id}/damage-calc" )
    @ApiOperation( "" )
    public ResponseEntity< MatchTurnResponse > damageCalc(  @PathVariable( value = "id" ) Long matchId ) {
        var matchTurnResponse = service.damageCalculate(matchId);
        return new ResponseEntity<>( matchTurnResponse , HttpStatus.CREATED );
    }

    @GetMapping( "/{id}/turn-logs" )
    @ApiOperation( "" )
    public ResponseEntity< List<TurnLog> > turnLogs(@PathVariable( value = "id" ) Long matchId  ) {

        var turnLogs = turnLogRepository.getByMatchId(matchId);
        return new ResponseEntity<>(turnLogs, HttpStatus.OK );
    }

}
