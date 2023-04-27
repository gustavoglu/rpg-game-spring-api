package com.avanade.rpg.service;

import java.util.Random;

public class DiceRpgService {

    public  static  int getResult(int diceAmount,int diceFaces){
        int result = 0;

        for (var index = 0; index < diceAmount; index++){
            Random random = new Random();
            result = result + random.nextInt(diceAmount,diceFaces);
        }

        return result;
    }

    public  static Integer getFirstTurnResult(){
        return getResult(1,20);
    }

    public  static Integer getAttackResult(){
     return  getResult(1,12);
    }

    public  static Integer getDefenseResult(){
        return getResult(1,12);
    }
}
