package com.avanade.rpg.requests;

public class MatchNewGameRequest {
    public String getPlayer1Name() {
        return player1Name;
    }

    public void setPlayer1Name(String player1Name) {
        this.player1Name = player1Name;
    }

    public Long getCharacter1Id() {
        return character1Id;
    }

    public void setCharacter1Id(Long character1Id) {
        this.character1Id = character1Id;
    }

    public String getPlayer2Name() {
        return player2Name;
    }

    public void setPlayer2Name(String player2Name) {
        this.player2Name = player2Name;
    }

    public Long getCharacter2Id() {
        return character2Id;
    }

    public void setCharacter2Id(Long character2Id) {
        this.character2Id = character2Id;
    }

    private String player1Name;
    private Long character1Id;
    private String player2Name;
    private Long character2Id;
}
