package com.avanade.rpg.responses;

import com.avanade.rpg.model.Match;
import com.avanade.rpg.model.TurnLog;

import java.util.List;

public class MatchTurnResponse {
    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public List<TurnLog> getTurnLogs() {
        return turnLogs;
    }

    public void setTurnLogs(List<TurnLog> turnLogs) {
        this.turnLogs = turnLogs;
    }

    private Match match;
    private List<TurnLog> turnLogs;
}
