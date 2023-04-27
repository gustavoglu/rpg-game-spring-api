package com.avanade.rpg.repository;

import com.avanade.rpg.model.Character;
import com.avanade.rpg.model.TurnLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TurnLogRepository extends JpaRepository<TurnLog, Long > {

    @Override
    @Query("from TurnLog c where c.isDeleted = false")
    List<TurnLog> findAll();
    @Query(value = "select * from TURN_LOG where MATCH_ID = ?1 AND TYPE = 0 order by CREATED_AT desc limit 1", nativeQuery = true)
    public TurnLog getLastAttackLogByMatchId(Long matchId);

    @Query(value = "select * from TURN_LOG where MATCH_ID = ?1 AND TYPE = 1 order by CREATED_AT desc limit 1", nativeQuery = true)
    public TurnLog getLastDefenseLogByMatchId(Long matchId);

    @Query(value = "select * from TURN_LOG where MATCH_ID = ?1 order by CREATED_AT desc limit 1", nativeQuery = true)
    public TurnLog getLastLogByMatchId(Long matchId);

    @Query(value = "select * from TURN_LOG where TOTAL_CHARACTER_DAMAGE > 0 AND Type = 1 and MATCH_ID = ?1 AND CHARACTER_ID = ?2 order by CREATED_AT desc limit 1", nativeQuery = true)
    public TurnLog getLastDefenseLogByMatchIdAndCharacterId(Long matchId,Long characterId);

    @Query("from TurnLog t join t.match m where m.id = ?1 order by t.createdAt")
    public List<TurnLog> getByMatchId(Long matchId);
}
