package com.avanade.rpg.repository;

import com.avanade.rpg.model.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CharacterRepository extends JpaRepository<Character, Long > {

    @Override
    @Query("from Character c where c.isDeleted = false")
    List<Character> findAll();

    @Query("from Character c where c.type = 1")
    List<Character> getMonsters();
}
