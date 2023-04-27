package com.avanade.rpg.service;

import com.avanade.rpg.enums.CharacterType;
import com.avanade.rpg.exception.ResourceNotFoundException;
import com.avanade.rpg.model.Character;
import com.avanade.rpg.repository.CharacterRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class CharacterService extends ServiceBase<Character,CharacterRepository> {


    private void createCharacterDefault(String name, CharacterType type, Integer health, Integer strength,Integer defense,
                                                    Integer agility,Integer diceAmount,Integer diceFaces){

        Character character = new Character();
        character.setName(name);
        character.setType(type);
        character.setHealth(health);
        character.setStrength(strength);
        character.setDefense(defense);
        character.setAgility(agility);
        character.setDiceAmount(diceAmount);
        character.setDiceFaces(diceFaces);

        create(character);
    }

    public  void createCharactersDefaultIfNotExists(){
        var characters = this.findAll();
        if(characters.size() > 0) return;
        createCharactersDefault();
    }

    public  void createCharactersDefault(){

        var characters = this.findAll();

        if(characters.stream().noneMatch(character -> character.getName().equals("Guerreiro"))) {
            createCharacterDefault("Guerreiro", CharacterType.Hero, 20, 7, 5, 6, 1, 12);
        }
        if(characters.stream().noneMatch(character -> character.getName().equals("Bárbaro"))) {
            createCharacterDefault("Bárbaro", CharacterType.Hero, 21, 10, 2, 5, 2, 8);
        }
        if(characters.stream().noneMatch(character -> character.getName().equals("Cavaleiro"))) {
            createCharacterDefault("Cavaleiro", CharacterType.Hero, 26, 6, 8, 3, 2, 6);
        }

        if(characters.stream().noneMatch(character -> character.getName().equals("Orc"))) {
            createCharacterDefault("Orc", CharacterType.Monster, 42, 7, 1, 2, 3, 4);
        }
        if(characters.stream().noneMatch(character -> character.getName().equals("Gigante"))) {
            createCharacterDefault("Gigante", CharacterType.Monster, 34, 10, 4, 4, 2, 6);
        }
        if(characters.stream().noneMatch(character -> character.getName().equals("Lobisomen"))) {
            createCharacterDefault("Lobisomen", CharacterType.Monster, 34, 7, 4, 7, 2, 4);
        }
    }

    public  Character getMonsterRandom(){

       List<Character> charactersMonsters =  this.repository.getMonsters();

      int count = charactersMonsters.size();

      Random random = new Random();
      int index = random.nextInt(count -1);

        var character = charactersMonsters.get(index);
        if(character == null) throw new ResourceNotFoundException("Any monster found");
        return character;
    }
}
