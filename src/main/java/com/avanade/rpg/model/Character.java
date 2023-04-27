package com.avanade.rpg.model;


import com.avanade.rpg.enums.CharacterType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table( name = "CHARACTER" )

public class Character extends EntityBase {

    @Column(name="TYPE", nullable = false)
    private CharacterType type;
    @Column(name="NAME", nullable = false)
    private String name;
    @Column(name="STRENGTH", nullable = false)
    private Integer strength;
    @Column(name="HEALTH", nullable = false)
    private Integer health;
    @Column(name="DEFENSE", nullable = false)
    private Integer defense;
    @Column(name="AGILITY", nullable = false)
    private Integer agility;
    @Column(name="DICE_AMOUNT", nullable = false)
    private Integer diceAmount;
    @Column(name="DICE_FACES", nullable = false)
    private Integer diceFaces;


    public Integer attackCalc(Integer diceAttackResult){
        return getStrength() + getAgility() + diceAttackResult;
    }

    public Integer defenseCalc(Integer diceDefenseResult){
        return getDefense() + getAgility() + diceDefenseResult;
    }


    public CharacterType getType() {
        return type;
    }

    public void setType(CharacterType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStrength() {
        return strength;
    }

    public void setStrength(Integer strength) {
        this.strength = strength;
    }

    public Integer getHealth() {
        return health;
    }

    public void setHealth(Integer health) {
        this.health = health;
    }

    public Integer getDefense() {
        return defense;
    }

    public void setDefense(Integer defense) {
        this.defense = defense;
    }

    public Integer getAgility() {
        return agility;
    }

    public void setAgility(Integer agility) {
        this.agility = agility;
    }

    public Integer getDiceAmount() {
        return diceAmount;
    }

    public void setDiceAmount(Integer diceAmount) {
        this.diceAmount = diceAmount;
    }

    public Integer getDiceFaces() {
        return diceFaces;
    }

    public void setDiceFaces(Integer diceFaces) {
        this.diceFaces = diceFaces;
    }


}
