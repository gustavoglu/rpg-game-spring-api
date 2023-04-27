package com.avanade.rpg.repository;

import com.avanade.rpg.model.Character;
import com.avanade.rpg.model.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long > {
    @Override
    @Query("from Match c where c.isDeleted = false")
    List<Match> findAll();
}
