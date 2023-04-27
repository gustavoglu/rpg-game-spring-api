package com.avanade.rpg.controller;

import com.avanade.rpg.service.CharacterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( value = "api/v1/character" )
@Api( value = "CHARACTERS API REST" )
@CrossOrigin( origins = "*" )
public class Character {

    @Autowired
    private CharacterService service;

    @PostMapping( "/create-default-characters" )
    @ApiOperation( "Create a default characters" )
    public ResponseEntity< List<com.avanade.rpg.model.Character> > createDefaultCharacters() {

        service.createCharactersDefault();

        var characters = service.findAll();

        return new ResponseEntity<>( characters, HttpStatus.CREATED );
    }


    @GetMapping( "/" )
    @ApiOperation( "find a characters in character list" )
    public ResponseEntity< List< com.avanade.rpg.model.Character > > getAll( ) {
        return new ResponseEntity<>( service.findAll( ), HttpStatus.OK );
    }

    @GetMapping( "/{id}" )
    @ApiOperation( "find a character by it's id in the character list" )
    public ResponseEntity< com.avanade.rpg.model.Character > getById(@PathVariable( value = "id" ) Long characterId ) {
        return new ResponseEntity<>( service.findById( characterId ), HttpStatus.OK );
    }

    @PostMapping( "/" )
    @ApiOperation( "Create a new character in character list" )
    public ResponseEntity< com.avanade.rpg.model.Character > create(@RequestBody com.avanade.rpg.model.Character entity ) {
        return new ResponseEntity<>( service.create( entity ), HttpStatus.CREATED );
    }

    @PutMapping( "/" )
    @ApiOperation( "Update a character on character list" )
    public ResponseEntity< com.avanade.rpg.model.Character > update(@RequestBody com.avanade.rpg.model.Character entity ) {
        return new ResponseEntity<>( service.update( entity ), HttpStatus.OK );
    }

    @DeleteMapping( "/" )
    @ApiOperation( "Delete a character on character list" )
    public ResponseEntity< HttpStatus > update( @RequestHeader Long id ) {
        service.delete( id );
        return new ResponseEntity<>( HttpStatus.NO_CONTENT );
    }


}
