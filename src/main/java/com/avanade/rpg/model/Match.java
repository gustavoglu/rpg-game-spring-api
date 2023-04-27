package com.avanade.rpg.model;


import com.avanade.rpg.enums.TurnType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table( name = "MATCH" )
public class Match extends EntityBase {

    @ManyToOne()
    @JoinColumn(name="PLAYER_WIN_CHARACTER_ID", referencedColumnName = "ID")
    private Character PlayerWinCharacter;

    @ManyToOne()
    @JoinColumn(name="CHARACTER_1_ID", referencedColumnName = "ID")
    private Character character1;

    @ManyToOne()
    @JoinColumn(name="CHARACTER_2_ID", referencedColumnName = "ID")
    private Character character2;

    @Column(name="CHARACTER_1_HEALTH", nullable = false)
    private Integer character1Health;

    @Column(name="CHARACTER_2_HEALTH", nullable = false)
    private Integer character2Health;

    @ManyToOne()
    @JoinColumn(name="TURN_CHARACTER_ID", referencedColumnName = "ID")
    private Character turnCharacter;

    @Column(name="TURN_TYPE")
    private TurnType turnType;

    @Column(name="DATE", nullable = false )
    private LocalDateTime date;
    @Column(name="IS_COMPLETE", nullable = false )
    private Boolean isComplete;

    @Column(name="PLAYER_1_NAME", nullable = false )
    private String player1Name;

    @Column(name="PLAYER_2_NAME" )
    private String player2Name;

    @Column(name="PLAYER_1_DECIDE_DICE_RESULT" )
    private Integer player1DecideDiceResult;
    @Column(name="PLAYER_2_DECIDE_DICE_RESULT" )
    private Integer player2DecideDiceResult;

    @Column(name="PLAYER_START" )
    private String PlayerStart;
    @ManyToOne()
    @JoinColumn(name="CHARACTER_START", referencedColumnName = "ID")
    private Character characterStart;

    public  String getPlayerNameByCharacterId(Long characterId){
        return characterId == getCharacter1().getId() ? player1Name : player2Name;
    }

    public String getPlayerStart() {
        return PlayerStart;
    }

    public void setPlayerStart(String playerStart) {
        PlayerStart = playerStart;
    }

    public Character getCharacterStart() {
        return characterStart;
    }

    public void setCharacterStart(Character characterStart) {
        this.characterStart = characterStart;
    }

    public Integer getPlayer1DecideDiceResult() {
        return player1DecideDiceResult;
    }

    public void setPlayer1DecideDiceResult(Integer player1DecideDiceResult) {
        this.player1DecideDiceResult = player1DecideDiceResult;
    }

    public Integer getPlayer2DecideDiceResult() {
        return player2DecideDiceResult;
    }

    public void setPlayer2DecideDiceResult(Integer player2DecideDiceResult) {
        this.player2DecideDiceResult = player2DecideDiceResult;
    }

    public Boolean CharactersNoHealthExists(){
        return getCharacter1Health() <= 0 || getCharacter2Health() <= 0;
    }

    public void setPlayerWinWithHealth(){
        if(!CharactersNoHealthExists()) return;
        var character = getCharacter1Health() <= 0 ? getCharacter2() : getCharacter1();
        setComplete(true);
        setPlayerWinCharacter(character);
    }

    public Character getNextTurnCharacter(){

        if(getTurnType() == TurnType.Attack) {
            return this.getCharacter1().getId() == getTurnCharacter().getId() ? getCharacter2() : getCharacter1();
        }else{
            return getTurnCharacter();
        }
    }

    public Character getPlayerWinCharacter() {
        return PlayerWinCharacter;
    }

    public void setPlayerWinCharacter(Character playerWinCharacter) {
        PlayerWinCharacter = playerWinCharacter;
    }



    public TurnType getTurnType() {
        return turnType;
    }

    public void setTurnType(TurnType turnType) {
        this.turnType = turnType;
    }

    public Integer getCharacterHealth(Long characterId){
        return getCharacter1().getId() == characterId ? character1Health : character2Health;
    }


    public String getPlayerNameWinning(){
        if(getPlayerWinCharacter() != null)
        return getPlayerWinCharacter().getId() == getCharacter1().getId() ? getPlayer1Name() : getPlayer2Name();

        return  null;
    }

    public Integer getCharacter1Health() {
        return character1Health;
    }

    public void setCharacterHealth(Long characterId, Integer health) {

        if(this.getCharacter1().getId() == characterId)
            setCharacter1Health(health);
        else
            setCharacter2Health(health);
    }

    public void setCharacter1Health(Integer character1Health) {
        this.character1Health = character1Health < 0 ? 0 : character1Health;
    }

    public Integer getCharacter2Health() {
        return character2Health;
    }

    public void setCharacter2Health(Integer character2Health) {
        this.character2Health = character2Health < 0 ? 0 : character2Health;
    }

    public String getPlayer1Name() {
        return player1Name;
    }

    public void setPlayer1Name(String player1Name) {
        this.player1Name = player1Name;
    }

    public String getPlayer2Name() {
        return player2Name;
    }

    public void setPlayer2Name(String player2Name) {
        this.player2Name = player2Name;
    }
    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Boolean getComplete() {
        return isComplete;
    }

    public void setComplete(Boolean complete) {
        isComplete = complete;
    }

    public Character getCharacter1() {
        return character1;
    }

    public void setCharacter1(Character character1) {
        this.character1 = character1;
    }

    public Character getCharacter2() {
        return character2;
    }

    public void setCharacter2(Character character2) {
        this.character2 = character2;
    }


    public Character getTurnCharacter() {
        return turnCharacter;
    }

    public void setTurnCharacter(Character turnCharacter) {
        this.turnCharacter = turnCharacter;
    }


}
