package com.avanade.rpg.service;

import com.avanade.rpg.enums.CharacterType;
import com.avanade.rpg.model.Character;
import com.avanade.rpg.repository.CharacterRepository;
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

public class CharacterServiceTest {
    @InjectMocks
    private CharacterService service;

    @Mock
    private CharacterRepository repository;

    List<Character> characterList;

    @BeforeEach
    void setUp( ) {
        MockitoAnnotations.openMocks( this );
        characterList = new ArrayList<>( );
        Character character1 = new Character(CharacterType.Hero,"Character 1",10,20,9,8,1,12);
        character1.setId(1L);
        character1.setId(2L);
        Character character2 =  new Character(CharacterType.Hero,"Character 2",10,20,9,8,1,12);
        characterList.add( character1 );
        characterList.add( character2 );
    }


    @Test
    void create( ) {
        Character character = new Character(CharacterType.Hero,"Character Name",10,20,9,8,1,12);
        character.setId(1L);
        Character expected =  new Character(CharacterType.Hero,"Character Name",10,20,9,8,1,12);
        expected.setId(1L);
        when( repository.save( character ) ).thenReturn( expected );
        Character response = service.create( character );
        Assertions.assertEquals( expected.getName( ), response.getName( ) );
        verify( repository, times( 1 ) ).save( any( ) );
    }


    @Test
    void findAll( ) {
        when( repository.findAll( ) ).thenReturn(characterList);
        List< Character > characters = service.findAll( );
        Assertions.assertEquals( characters, this.characterList);
        verify( repository, times( 1 ) ).findAll( );
    }

    @Test
    void findById( ) {
        when( repository.findById( any( ) ) ).thenReturn(
                Optional.ofNullable( characterList.get( 0 ) ) );
        Character character = service.findById( 1L );
        Assertions.assertEquals( character, characterList.get( 0 ) );
        verify( repository, times( 1 ) ).findById( any( ) );
    }

    @Test
    void delete( ) {
        when( repository.findById( any( ) ) ).thenReturn(
                Optional.ofNullable( characterList.get( 0 ) ) );

        Character character = characterList.get( 0 );
        character.setIsDeleted( false );
        character.setCreatedAt( LocalDateTime.now( ) );
        when( repository.save(character) ).thenReturn(character);

        service.delete( 1L );

        verify( repository, times( 1 ) ).save( any( ) );
    }

    @Test
    void update( ) {
        Character character = characterList.get( 0 );
        character.setIsDeleted( false );
        character.setCreatedAt( LocalDateTime.now( ) );
        when( repository.save(character) ).thenReturn(character);
        Character response = service.update(character);
        Assertions.assertEquals( character.getIsDeleted( ), response.getIsDeleted( ) );
        verify( repository, times( 1 ) ).save( any( ) );
    }
}
