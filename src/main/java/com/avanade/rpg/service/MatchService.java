package com.avanade.rpg.service;

import com.avanade.rpg.enums.TurnType;
import com.avanade.rpg.exception.InvalidInputException;
import com.avanade.rpg.exception.InvalidTurnActionException;
import com.avanade.rpg.exception.ResourceNotFoundException;
import com.avanade.rpg.model.Character;
import com.avanade.rpg.model.Match;
import com.avanade.rpg.model.TurnLog;
import com.avanade.rpg.repository.MatchRepository;
import com.avanade.rpg.repository.TurnLogRepository;
import com.avanade.rpg.responses.MatchTurnResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MatchService extends ServiceBase<Match,MatchRepository> {

    @Autowired
    private CharacterService characterService;
    @Autowired
    private TurnLogService turnLogService;
    @Autowired
    private TurnLogRepository turnLogRepository;


    public Match newGame(String player1Name, Long character1Id ,String player2Name, Long character2Id) {

        if(character1Id == null) {throw new InvalidInputException("character1Id is required");}
        if(player1Name == null) {throw new InvalidInputException("player1Name is required");}

        var character1 = characterService.findById(character1Id);

        Match match = new Match();
        Character character2 ;

        if(character2Id != null) {
            character2 = characterService.findById(character2Id);

        }else{
            character2 = characterService.getMonsterRandom();
        }

        match.setPlayer1Name(player1Name);
        match.setCharacter1(character1);

        match.setPlayer2Name(player2Name == null ? "Computer" : player2Name);
        match.setCharacter2(character2);

        LocalDateTime now = LocalDateTime.now( );

        match.setDate( now );
        match.setCreatedAt( now );

        match.setIsDeleted( false );
        match.setComplete( false );
        match.setCharacter1Health(character1.getHealth());
        match.setCharacter2Health(character2.getHealth());

        decideFirstCharacterIdAttack(match);

        match.setTurnType(TurnType.Attack);

        return this.repository.save( match );
    }
    private void decideFirstCharacterIdAttack(Match match){

        Integer character1DiceResult = 0;
        Integer character2DiceResult =  0;

        while(character1DiceResult.equals(character2DiceResult)) {
            character1DiceResult = DiceRpgService.getFirstTurnResult();
            character2DiceResult = DiceRpgService.getFirstTurnResult();
        }

        match.setPlayer1DecideDiceResult(character1DiceResult);
        match.setPlayer2DecideDiceResult(character2DiceResult);

        var turnCharacter = character1DiceResult > character2DiceResult ? match.getCharacter1() : match.getCharacter2();

        match.setTurnCharacter(turnCharacter);
        match.setCharacterStart(turnCharacter);
        match.setPlayerStart(match.getPlayerNameByCharacterId(turnCharacter.getId()));
    }
    public MatchTurnResponse attackTurn(Long matchId){
        var match = this.findById(matchId);

        if(match.getIsComplete()){
             throw new InvalidTurnActionException("Match is complete, Winner: " + match.getPlayerNameWinning());
        }

        if(match.getTurnType() != TurnType.Attack) {
            throw new InvalidTurnActionException( "It's not the attack turn" );
        }

       var lastTurnLog = turnLogRepository.getLastLogByMatchId(matchId);
        if(lastTurnLog != null && !lastTurnLog.isDamageCalculated()){
            throw new InvalidTurnActionException( "It is not possible to attack without first calculating the damage " );
        }


        var characterAttack = match.getTurnCharacter();

        var characterAttackDiceResult = DiceRpgService.getAttackResult();
        var characterAttackTotal = characterAttack.attackCalc(characterAttackDiceResult);

        var turnLog = this.createTurnLog(match,characterAttack,TurnType.Attack,characterAttackDiceResult,characterAttackTotal);

        var nextTurnCharacter = match.getNextTurnCharacter();

        match.setTurnType(TurnType.Defense);
        match.setTurnCharacter(nextTurnCharacter);

        update(match);
        turnLogService.create(turnLog);

        return  getMatchTurnResponse(matchId);
    }

    private MatchTurnResponse getMatchTurnResponse(Long matchId){
        var match = this.findById(matchId);
        List<TurnLog> turnLogs = turnLogRepository.getByMatchId(matchId);
        MatchTurnResponse matchTurnResponse = new MatchTurnResponse();
        matchTurnResponse.setMatch(match);
        matchTurnResponse.setTurnLogs(turnLogs);
        return  matchTurnResponse;
    }

    public MatchTurnResponse damageCalculate(long matchId){
        int diceDamageStrength;

        var match = this.findById(matchId);

        if(match.getIsComplete()){
            throw new InvalidTurnActionException("Match is complete, Winner: " + match.getPlayerNameWinning());
        }

        var defenseTurnLog = turnLogRepository.getLastLogByMatchId(matchId);

       if(defenseTurnLog.getType() != TurnType.Defense  || defenseTurnLog.getDamage() != null){
           throw new InvalidTurnActionException("It is necessary to defend on the turn before calculating the damage");
       }

        if(defenseTurnLog.isDamageCalculated()){
            throw new InvalidTurnActionException("The damage has already been calculated");
        }

       var attackTurnLog =  turnLogRepository.getLastAttackLogByMatchId(matchId);

       if(attackTurnLog == null){  throw new ResourceNotFoundException("Turn Attack not found");}

        boolean isDamage = attackTurnLog.getDiceResultCalc() > defenseTurnLog.getDiceResultCalc();

        if(isDamage){

            var characterDefense = defenseTurnLog.getCharacter();

            var characterAttack = attackTurnLog.getCharacter();

            var diceAmountCharacterAttack = characterAttack.getDiceAmount();
            var diceFacesCharacterAttack = characterAttack.getDiceFaces();

            var diceDamageResult = DiceRpgService.getResult(diceAmountCharacterAttack,diceFacesCharacterAttack);
            diceDamageStrength = diceDamageResult + characterAttack.getStrength();

            defenseTurnLog.setDiceDamageResult(diceDamageResult);
            defenseTurnLog.setDiceDamageStrength(characterAttack.getStrength());

            var characterDefenseHealth = match.getCharacterHealth(characterDefense.getId()) - diceDamageStrength;
            match.setCharacterHealth(characterDefense.getId(),characterDefenseHealth);

            defenseTurnLog.setDamage(diceDamageStrength);

            var lastDefenseLogCharacter = turnLogRepository.getLastDefenseLogByMatchIdAndCharacterId(matchId,characterDefense.getId());
            if(lastDefenseLogCharacter != null && lastDefenseLogCharacter.getDamage() > 0) {
                var damageTotal = lastDefenseLogCharacter.getTotalCharacterDamage() + diceDamageStrength;
                defenseTurnLog.setTotalCharacterDamage(damageTotal);
            }else{
                defenseTurnLog.setTotalCharacterDamage(diceDamageStrength);
            }
        }

        if(match.CharactersNoHealthExists())
            match.setPlayerWinWithHealth();

        defenseTurnLog.setDamageCalculated(true);

        update(match);
        turnLogService.update(defenseTurnLog);
        return getMatchTurnResponse(matchId);

    }
    public MatchTurnResponse defenseTurn(Long matchId){
        var match = this.findById(matchId);

        if(match.getIsComplete()){
            throw new InvalidTurnActionException("Match is complete, Winner: " + match.getPlayerNameWinning());
        }

        if(match.getTurnType() != TurnType.Defense) {throw new InvalidTurnActionException( "It's not the defense turn" );}

        var characterDefense = match.getTurnCharacter();

        var characterDefenseDiceResult = DiceRpgService.getDefenseResult();
        var characterDefenseTotal = characterDefense.defenseCalc(characterDefenseDiceResult);

        var turnLog = this.createTurnLog(match,characterDefense,TurnType.Defense,characterDefenseDiceResult,characterDefenseTotal);

        var nextTurnCharacter = match.getNextTurnCharacter();
        match.setTurnCharacter(nextTurnCharacter);
        match.setTurnType(TurnType.Attack);

        update(match);
        turnLogService.create(turnLog);

        return getMatchTurnResponse(matchId);
    }


    private TurnLog createTurnLog(Match match, Character turnCharacter, TurnType turnType,
                                                Integer diceResult, Integer diceResultCalc){
        var turnLog = new TurnLog();

        turnLog.setAgility(turnCharacter.getAgility());
        turnLog.setValueCalc(turnCharacter.getDefense());
        turnLog.setMatch(match);
        turnLog.setType(turnType);
        turnLog.setCharacter(turnCharacter);
        turnLog.setDiceResult(diceResult);
        turnLog.setDiceResultCalc(diceResultCalc);

        return turnLog;
    }


}
