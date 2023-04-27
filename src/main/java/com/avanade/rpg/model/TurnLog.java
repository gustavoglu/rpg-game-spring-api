package com.avanade.rpg.model;

import com.avanade.rpg.enums.TurnType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table( name = "TURN_LOG" )
public class TurnLog extends EntityBase {

    @ManyToOne()
    @JoinColumn(name="MATCH_ID", referencedColumnName = "ID", nullable = false)
    private Match match;

    @Column(name="TYPE", nullable = false )
    private TurnType type;

    @ManyToOne()
    @JoinColumn(name="CHARACTER_ID", referencedColumnName = "ID", nullable = false )
    private Character character;

    @Column(name="DICE_RESULT", nullable = false )
    private Integer diceResult;
    @Column(name="DICE_RESULT_CALC", nullable = false )
    private Integer diceResultCalc;
    @Column(name="DAMAGE")
    private Integer damage;
    @Column(name="TOTAL_CHARACTER_DAMAGE" )
    private Integer totalCharacterDamage;
    @Column(name="VALUE_CALC", nullable = false )
    private Integer valueCalc;
    @Column(name="AGILITY", nullable = false )
    private Integer agility;
    @Column(name="DICE_DAMAGE_RESULT" )
    private Integer diceDamageResult;
    @Column(name="DICE_DAMAGE_STRENGTH")
    private Integer diceDamageStrength;

    public boolean isDamageCalculated() {
        return damageCalculated;
    }

    public void setDamageCalculated(boolean damageCalculated) {
        this.damageCalculated = damageCalculated;
    }

    @Column(name="DAMAGE_CALCULATED")
    private boolean damageCalculated;




    public Integer getDiceDamageResult() {
        return diceDamageResult;
    }

    public void setDiceDamageResult(Integer diceDamageResult) {
        this.diceDamageResult = diceDamageResult;
    }

    public Integer getDiceDamageStrength() {
        return diceDamageStrength;
    }

    public void setDiceDamageStrength(Integer diceDamageStrength) {
        this.diceDamageStrength = diceDamageStrength;
    }


    public Integer getTotalCharacterDamage() {
        return totalCharacterDamage;
    }

    public void setTotalCharacterDamage(Integer totalCharacterDamage) {
        this.totalCharacterDamage = totalCharacterDamage;
    }

    public TurnType getType() {
        return type;
    }

    public void setType(TurnType type) {
        this.type = type;
    }

    public Match getMatch() {
    return match;
}

    public void setMatch(Match match) {
        this.match = match;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public Integer getDiceResult() {
        return diceResult;
    }

    public void setDiceResult(Integer diceResult) {
        this.diceResult = diceResult;
    }

    public Integer getDiceResultCalc() {
        return diceResultCalc;
    }

    public void setDiceResultCalc(Integer diceResultCalc) {
        this.diceResultCalc = diceResultCalc;
    }

    public Integer getDamage() {
        return damage;
    }

    public void setDamage(Integer damage) {
        this.damage = damage;
    }

    public Integer getValueCalc() {
        return valueCalc;
    }

    public void setValueCalc(Integer valueCalc) {
        this.valueCalc = valueCalc;
    }

    public Integer getAgility() {
        return agility;
    }

    public void setAgility(Integer agility) {
        this.agility = agility;
    }

}
