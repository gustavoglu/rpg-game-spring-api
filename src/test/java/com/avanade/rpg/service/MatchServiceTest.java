package com.avanade.rpg.service;

import com.avanade.rpg.enums.CharacterType;
import com.avanade.rpg.enums.TurnType;
import com.avanade.rpg.model.Character;
import com.avanade.rpg.model.Match;
import com.avanade.rpg.model.TurnLog;
import com.avanade.rpg.repository.CharacterRepository;
import com.avanade.rpg.repository.MatchRepository;
import com.avanade.rpg.repository.TurnLogRepository;
import com.avanade.rpg.responses.MatchTurnResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MatchServiceTest {
    @InjectMocks
    private MatchService service;
    @Mock
    private TurnLogService turnLogService;


    @Mock
    private MatchRepository repository;

    @Mock
    private CharacterService characterService;
    @Mock
    private TurnLogRepository turnLogRepository;

    List<Match> matchList;

    @BeforeEach
    void setUp( ) {
        MockitoAnnotations.openMocks( this );
        matchList = new ArrayList<>( );
        Character character1 =new Character(CharacterType.Hero,"Character 1",10,20,9,8,1,12);
        Character character2 =new Character(CharacterType.Monster,"Character 2",10,20,9,8,1,12);
        character1.setId(1L);
        character2.setId(2L);


        Match match1 = new Match(null,character1,character2,40,20,character1, TurnType.Attack,LocalDateTime.now(),false,"Player 1","Player 2",10,8,"Player 1",character1);
        match1.setId(1L);
        Match match2 = new Match(null,character2,character1,40,20,character2, TurnType.Defense,LocalDateTime.now(),false,"Player 1","Player 2",10,8,"Player 1",character1);
        match2.setId(2L);
        matchList.add( match1 );
        matchList.add( match2 );

    }

    @Test
    void damageCalculate(){
        Character character1 =new Character(CharacterType.Hero,"Character 1",10,20,9,8,1,12);
        Character character2 =new Character(CharacterType.Monster,"Character 2",10,20,9,8,1,12);
        character1.setId(1L);
        character2.setId(2L);

        Match match1 = new Match(null,character1,character2,40,20,character1, TurnType.Defense,LocalDateTime.now(),false,"Player 1","Player 2",10,8,"Player 1",character1);
        match1.setId(1L);
        Match expected = new Match(null,character1,character2,40,20,character1, TurnType.Defense,LocalDateTime.now(),false,"Player 1","Player 2",10,8,"Player 1",character1);
        expected.setId(1L);

        MatchTurnResponse expectedResponse = new MatchTurnResponse();
        expectedResponse.setMatch(match1);
        expectedResponse.setTurnLogs(new ArrayList<>());

        when( repository.findById( any( ) ) ).thenReturn(Optional.ofNullable( match1 ) );

        var turnLogDefense = new TurnLog(match1,TurnType.Defense,character1,10,18,null,null,7,10,null,null,false);
        var turnLogAttack = new TurnLog(match1,TurnType.Attack,character2,10,20,null,null,7,10,null,null,false);

        when(turnLogRepository.getLastLogByMatchId(any())).thenReturn(turnLogDefense);

        when(turnLogRepository.getLastAttackLogByMatchId(any())).thenReturn(turnLogAttack);

        when(turnLogRepository.getLastDefenseLogByMatchIdAndCharacterId(any(),any())).thenReturn(null);

        when( turnLogRepository.getByMatchId( any( ) ) ).thenReturn( new ArrayList<>() );

        when( repository.save(any()) ).thenReturn(match1);

        var response = service.damageCalculate(match1.getId());

        Assertions.assertEquals( expectedResponse.getMatch().getId(), response.getMatch( ).getId() );
        verify( repository, times( 2 ) ).findById( any( ) );
        verify( turnLogRepository, times( 1 ) ).getLastAttackLogByMatchId( any( ) );
        verify( turnLogRepository, times( 1 ) ).getLastDefenseLogByMatchIdAndCharacterId( any( ),any() );
    }




    @Test
    void defenseTurn(){
        Character character1 =new Character(CharacterType.Hero,"Character 1",10,20,9,8,1,12);
        Character character2 =new Character(CharacterType.Monster,"Character 2",10,20,9,8,1,12);
        character1.setId(1L);
        character2.setId(2L);

        Match match1 = new Match(null,character1,character2,40,20,character1, TurnType.Defense,LocalDateTime.now(),false,"Player 1","Player 2",10,8,"Player 1",character1);
        match1.setId(1L);
        Match expected = new Match(null,character1,character2,40,20,character1, TurnType.Defense,LocalDateTime.now(),false,"Player 1","Player 2",10,8,"Player 1",character1);
        expected.setId(1L);

        MatchTurnResponse expectedResponse = new MatchTurnResponse();
        expectedResponse.setMatch(match1);
        expectedResponse.setTurnLogs(new ArrayList<>());

        when(turnLogRepository.getLastLogByMatchId(any())).thenReturn(null);

        when( repository.findById( any( ) ) ).thenReturn(Optional.ofNullable( match1 ) );

        when( turnLogRepository.getByMatchId( any( ) ) ).thenReturn( new ArrayList<>() );

        when( repository.save(any()) ).thenReturn(match1);

        when( repository.save(any()) ).thenReturn(match1);

        var response = service.defenseTurn(match1.getId());

        Assertions.assertEquals( expectedResponse.getMatch().getId(), response.getMatch( ).getId() );
        verify( repository, times( 2 ) ).findById( any( ) );
        verify( turnLogRepository, times( 1 ) ).getByMatchId( any( ) );
    }



    @Test
    void attackTurn(){
        Character character1 =new Character(CharacterType.Hero,"Character 1",10,20,9,8,1,12);
        Character character2 =new Character(CharacterType.Monster,"Character 2",10,20,9,8,1,12);
        character1.setId(1L);
        character2.setId(2L);

        Match match1 = new Match(null,character1,character2,40,20,character1, TurnType.Attack,LocalDateTime.now(),false,"Player 1","Player 2",10,8,"Player 1",character1);
        match1.setId(1L);
        Match expected = new Match(null,character1,character2,40,20,character1, TurnType.Attack,LocalDateTime.now(),false,"Player 1","Player 2",10,8,"Player 1",character1);
        expected.setId(1L);

        MatchTurnResponse expectedResponse = new MatchTurnResponse();
        expectedResponse.setMatch(match1);
        expectedResponse.setTurnLogs(new ArrayList<>());

        when(turnLogRepository.getLastLogByMatchId(any())).thenReturn(null);

        when( repository.findById( any( ) ) ).thenReturn(Optional.ofNullable( match1 ) );

        when( turnLogRepository.getByMatchId( any( ) ) ).thenReturn( new ArrayList<>() );

        when( repository.save(any()) ).thenReturn(match1);

        when( repository.save(any()) ).thenReturn(match1);

        var response = service.attackTurn(match1.getId());

        Assertions.assertEquals( expectedResponse.getMatch().getId(), response.getMatch( ).getId() );
        verify( repository, times( 2 ) ).findById( any( ) );
        verify( turnLogRepository, times( 1 ) ).getByMatchId( any( ) );
    }


    @Test
    void newGame(){

        Character character1 =new Character(CharacterType.Hero,"Character 1",10,20,9,8,1,12);
        Character character2 =new Character(CharacterType.Monster,"Character 2",10,20,9,8,1,12);
        character1.setId(1L);
        character2.setId(2L);

        when( characterService.findById( any( ) ) ).thenReturn(character1);

        var character1response = characterService.findById(1L);

        when( characterService.findById( any( ) ) ).thenReturn(character2);

        var character1response2 = characterService.findById(2L);

        verify( characterService, times( 2 ) ).findById( any( ) );

       Match match1 = new Match(null,character1response,character1response2,40,20,character1, TurnType.Attack,LocalDateTime.now(),false,"Player 1","Player 2",10,8,"Player 1",character1);
        match1.setId(1L);
        Match expected = new Match(null,character1response,character1response2,40,20,character1, TurnType.Attack,LocalDateTime.now(),false,"Player 1","Player 2",10,8,"Player 1",character1);
        expected.setId(1L);

        when(repository.save(any())).thenReturn(expected);
        Match response = service.newGame(match1.getPlayer1Name(),match1.getCharacter1().getId(),match1.getPlayer2Name(),match1.getCharacter2().getId());
        Assertions.assertEquals( expected.getPlayer1Name( ), response.getPlayer1Name( ) );
        verify( repository, times( 1 ) ).save( any( ) );
    }

    @Test
    void create( ) {
        Character character1 =new Character(CharacterType.Hero,"Character 1",10,20,9,8,1,12);
        Character character2 =new Character(CharacterType.Hero,"Character 2",10,20,9,8,1,12);

        Match match1 = new Match(null,character1,character2,40,20,character1, TurnType.Attack,LocalDateTime.now(),false,"Player 1","Player 2",10,8,"Player 1",character1);
        match1.setId(1L);
        Match expected = new Match(null,character1,character2,40,20,character1, TurnType.Attack,LocalDateTime.now(),false,"Player 1","Player 2",10,8,"Player 1",character1);
        expected.setId(2L);

        when( repository.save( match1 ) ).thenReturn( expected );
        Match response = service.create( match1 );
        Assertions.assertEquals( expected.getPlayer1Name( ), response.getPlayer1Name( ) );
        verify( repository, times( 1 ) ).save( any( ) );
    }


    @Test
    void findAll( ) {
        when( repository.findAll( ) ).thenReturn(matchList);
        List< Match > matchs = service.findAll( );
        Assertions.assertEquals(matchs, this.matchList);
        verify( repository, times( 1 ) ).findAll( );
    }

    @Test
    void findById( ) {
        when( repository.findById( any( ) ) ).thenReturn(
                Optional.ofNullable( matchList.get( 0 ) ) );
        Match match = service.findById( 1L );
        Assertions.assertEquals(match, matchList.get( 0 ) );
        verify( repository, times( 1 ) ).findById( any( ) );
    }

    @Test
    void delete( ) {
        when( repository.findById( any( ) ) ).thenReturn(
                Optional.ofNullable( matchList.get( 0 ) ) );

        Match match = matchList.get( 0 );
        match.setIsDeleted( false );
        match.setCreatedAt( LocalDateTime.now( ) );
        when( repository.save(match) ).thenReturn(match);

        service.delete( 1L );

        verify( repository, times( 1 ) ).save( any( ) );
    }

    @Test
    void update( ) {
        Match match = matchList.get( 0 );
        match.setIsDeleted( false );
        match.setCreatedAt( LocalDateTime.now( ) );
        when( repository.save(match) ).thenReturn(match);
        Match response = service.update(match);
        Assertions.assertEquals( match.getIsDeleted( ), response.getIsDeleted( ) );
        verify( repository, times( 1 ) ).save( any( ) );
    }
}
