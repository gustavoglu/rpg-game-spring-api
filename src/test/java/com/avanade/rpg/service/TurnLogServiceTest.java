package com.avanade.rpg.service;

import com.avanade.rpg.enums.CharacterType;
import com.avanade.rpg.enums.TurnType;
import com.avanade.rpg.model.Character;
import com.avanade.rpg.model.Match;
import com.avanade.rpg.model.TurnLog;
import com.avanade.rpg.repository.TurnLogRepository;
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

public class TurnLogServiceTest {
    @InjectMocks
    private TurnLogService service;

    @Mock
    private TurnLogRepository repository;

    List<TurnLog> turnLogList;

    @BeforeEach
    void setUp( ) {
        MockitoAnnotations.openMocks( this );
        turnLogList = new ArrayList<>( );

        Character character1 =new Character(CharacterType.Hero,"Character 1",10,20,9,8,1,12);
        Character character2 =new Character(CharacterType.Hero,"Character 2",10,20,9,8,1,12);

        Match match = new Match(null,character1,character2,40,20,character1,TurnType.Attack,LocalDateTime.now(),false,"Player 1","Player 2",10,8,"Player 1",character1);
        TurnLog turnLog1 = new TurnLog(match,TurnType.Attack,character2,1,2,null,null,7,10,null,null,false);
        TurnLog turnLog2 = new TurnLog(match,TurnType.Attack,character2,1,2,null,null,7,10,null,null,false);
        turnLog1.setId(1L);
        turnLog2.setId(2L);

        turnLogList.add(turnLog1);
        turnLogList.add(turnLog2);
    }


    @Test
    void create( ) {
        Character character1 =new Character(CharacterType.Hero,"Character 1",10,20,9,8,1,12);
        Character character2 =new Character(CharacterType.Hero,"Character 2",10,20,9,8,1,12);
        Match match = new Match(null,character1,character2,40,20,character1,TurnType.Attack,LocalDateTime.now(),false,"Player 1","Player 2",10,8,"Player 1",character1);
        TurnLog turnLog = new TurnLog(match,TurnType.Attack,character2,1,2,null,null,7,10,null,null,false);
        turnLog.setId(1L);
        TurnLog expected = new TurnLog(match,TurnType.Attack,character2,1,2,null,null,7,10,null,null,false);
        expected.setId(1L);

        when( repository.save( turnLog ) ).thenReturn( expected );
        TurnLog response = service.create( turnLog );
        Assertions.assertEquals( expected.getId(), response.getId() );
        verify( repository, times( 1 ) ).save( any( ) );
    }


    @Test
    void findAll( ) {
        when( repository.findAll( ) ).thenReturn(turnLogList);
        List< TurnLog > turnLogs = service.findAll( );
        Assertions.assertEquals(turnLogs, this.turnLogList);
        verify( repository, times( 1 ) ).findAll( );
    }

    @Test
    void findById( ) {
        when( repository.findById( any( ) ) ).thenReturn(
                Optional.ofNullable( turnLogList.get( 0 ) ) );
        TurnLog turnLog = service.findById( 1L );
        Assertions.assertEquals(turnLog, turnLogList.get( 0 ) );
        verify( repository, times( 1 ) ).findById( any( ) );
    }

    @Test
    void delete( ) {
        when( repository.findById( any( ) ) ).thenReturn(
                Optional.ofNullable( turnLogList.get( 0 ) ) );

        TurnLog turnLog = turnLogList.get( 0 );
        turnLog.setIsDeleted( false );
        turnLog.setCreatedAt( LocalDateTime.now( ) );
        when( repository.save(turnLog) ).thenReturn(turnLog);

        service.delete( 1L );

        verify( repository, times( 1 ) ).save( any( ) );
    }

    @Test
    void update( ) {
        TurnLog turnLog = turnLogList.get( 0 );
        turnLog.setIsDeleted( false );
        turnLog.setCreatedAt( LocalDateTime.now( ) );
        when( repository.save(turnLog) ).thenReturn(turnLog);
        TurnLog response = service.update(turnLog);
        Assertions.assertEquals( turnLog.getIsDeleted( ), response.getIsDeleted( ) );
        verify( repository, times( 1 ) ).save( any( ) );
    }
}
