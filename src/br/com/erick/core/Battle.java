package br.com.erick.core;

import br.com.erick.model.Monster;
import br.com.erick.model.Player;

import java.util.concurrent.ThreadLocalRandom;

import static br.com.erick.constants.Constants.ATTACK_VARIATION_RATE;
import static br.com.erick.constants.Constants.DEFENSE_VARIATION_RATE;
import static br.com.erick.constants.Constants.DRAW;
import static br.com.erick.constants.Constants.MONSTER_A_WON;
import static br.com.erick.constants.Constants.MONSTER_B_WON;
import static br.com.erick.constants.Constants.PAPER;
import static br.com.erick.constants.Constants.ROCK;
import static br.com.erick.constants.Constants.SCISSORS;

/**
 * Created by erick.budal on 10/10/2017.
 */
public class Battle {
    private Player playerA;
    private Player playerB;
    private int actualTurn = 0;
    private String logString;

    public Battle(Player playerA, Player playerB) {
        this.playerA = playerA;
        this.playerB = playerB;
    }

    public Player getPlayerA() {
        return playerA;
    }
    public void setPlayerA(Player playerA) {
        this.playerA = playerA;
    }
    public Player getPlayerB() {
        return playerB;
    }
    public void setPlayerB(Player playerB) {
        this.playerB = playerB;
    }
    public int getActualTurn() {
        return actualTurn;
    }
    public void setActualTurn(int actualTurn) {
        this.actualTurn = actualTurn;
    }
    public String getLogString() {
        return logString;
    }
    public void setLogString(String logString) {
        this.logString = logString;
    }

    public void fight(int typeMonsterA, int typeMonsterB, boolean isUsingReverseA, boolean isUsingReverseB){
        Monster monsterPlayerA = getMonsterByType(typeMonsterA, true);
        Monster monsterPlayerB = getMonsterByType(typeMonsterB, false);

        int whoWon = whichMonsterWon(monsterPlayerA, monsterPlayerB, isUsingReverseA ^ isUsingReverseB); //XOR logical operator
        if(whoWon == MONSTER_A_WON){
            double strengh = isUsingReverseA ? monsterPlayerA.getStrength() * 1.5 : monsterPlayerA.getStrength(); //monster A
            double defense = isUsingReverseB ? monsterPlayerB.getDefense() * 1.5 : monsterPlayerB.getDefense();  //monster B
            double pureAttack = ThreadLocalRandom.current().nextDouble(
                    strengh * (1 - ATTACK_VARIATION_RATE), strengh * (1 + ATTACK_VARIATION_RATE));
            double pureDefense = ThreadLocalRandom.current().nextDouble(
                    defense * (1 - DEFENSE_VARIATION_RATE), defense * (1 + DEFENSE_VARIATION_RATE));
            int damage = (int)(pureAttack * (pureDefense / 100.0));

            playerB.setLifePoints(playerB.getLifePoints() - damage);

            logString = "You won!  Damage caused: " + damage + ".";
            logString += (isUsingReverseA) ? "  (Used Reverse!)" : "";
            System.out.println("You won!  Damage caused: " + "\u001B[31m" + damage + "\u001B[0m");
        }
        else if(whoWon == MONSTER_B_WON){
            double strengh = isUsingReverseB ? monsterPlayerB.getStrength() * 1.5 : monsterPlayerB.getStrength(); //monster B
            double defense = isUsingReverseA ? monsterPlayerA.getDefense() * 1.5 : monsterPlayerA.getDefense();  //monster A
            double pureAttack = ThreadLocalRandom.current().nextDouble(strengh * (1 - ATTACK_VARIATION_RATE), strengh * (1 + ATTACK_VARIATION_RATE));
            double pureDefense = ThreadLocalRandom.current().nextDouble(defense * (1 - DEFENSE_VARIATION_RATE), defense * (1 + DEFENSE_VARIATION_RATE));
            int damage = (int)(pureAttack * (pureDefense / 100.0));

            logString = "Enemy won!  Damage caused: " + damage;
            logString += (isUsingReverseB) ? "  (Used Reverse!)" : "";
            playerA.setLifePoints(playerA.getLifePoints() - damage);

            System.out.println("Enemy won!  Damage caused: " + "\u001B[31m" + damage + "\u001B[0m");
        }
        else{ //Draw
            int strengh = monsterPlayerA.getStrength(); //monster A
            int defense = monsterPlayerB.getDefense();  //monster B
            double pureAttack = ThreadLocalRandom.current().nextDouble(strengh * (1 - ATTACK_VARIATION_RATE), strengh * (1 + ATTACK_VARIATION_RATE));
            double pureDefense = ThreadLocalRandom.current().nextDouble(defense * (1 - DEFENSE_VARIATION_RATE), defense * (1 + DEFENSE_VARIATION_RATE));
            int damageToB = (int)(pureAttack * (pureDefense / 100.0));

            strengh = monsterPlayerB.getStrength(); //monster B

            defense = monsterPlayerA.getDefense();  //monster A
            pureAttack = ThreadLocalRandom.current().nextDouble(strengh * (1 - ATTACK_VARIATION_RATE), strengh * (1 + ATTACK_VARIATION_RATE));
            pureDefense = ThreadLocalRandom.current().nextDouble(defense * (1 - DEFENSE_VARIATION_RATE), defense * (1 + DEFENSE_VARIATION_RATE));
            int damageToA = (int)(pureAttack * (pureDefense / 100.0));
            playerA.setLifePoints(playerA.getLifePoints() - damageToA);
            playerB.setLifePoints(playerB.getLifePoints() - damageToB);

            logString = "Draw!  Damage to you:       " + damageToA + "\n             Damage to enemy: " + damageToB;

            System.out.println("Draw!");
            System.out.println("Damage to you:   " + "\u001B[31m" + damageToA + "\u001B[0m");
            System.out.println("Damage to enemy: " + "\u001B[31m" + damageToB + "\u001B[0m");
        }

        setAlreadyUsedMonsters(monsterPlayerA.getType(), monsterPlayerB.getType());

        System.out.println();
        System.out.println("------------------ Player A ------------------");
        System.out.println("HP:              " + "\u001B[32m" + playerA.getLifePoints() + "\u001B[0m");
        System.out.println("Rock Monster:    " + playerA.getRockMonster().isCanUse());
        System.out.println("Paper Monster:   " + playerA.getPaperMonster().isCanUse());
        System.out.println("Scissor Monster: " + playerA.getScissorMonster().isCanUse());
        System.out.println();
        System.out.println("------------------ Player B ------------------");
        System.out.println("HP:              " + "\u001B[32m" + playerB.getLifePoints() + "\u001B[0m");
        System.out.println("Rock Monster:    " + playerB.getRockMonster().isCanUse());
        System.out.println("Paper Monster:   " + playerB.getPaperMonster().isCanUse());
        System.out.println("Scissor Monster: " + playerB.getScissorMonster().isCanUse());

        actualTurn++;
    }

    private int whichMonsterWon(Monster monsterA, Monster monsterB, boolean isUsingReverse){

        int fightResult;

        if(monsterA.getType() == ROCK && monsterB.getType() == SCISSORS){
            fightResult = MONSTER_A_WON;
        }
        else if(monsterA.getType() == PAPER && monsterB.getType() == ROCK){
            fightResult = MONSTER_A_WON;
        }
        else if(monsterA.getType() == SCISSORS && monsterB.getType() == PAPER){
            fightResult = MONSTER_A_WON;
        }
        else if(monsterB.getType() == ROCK && monsterA.getType() == SCISSORS){
            fightResult = MONSTER_B_WON;
        }
        else if(monsterB.getType() == PAPER && monsterA.getType() == ROCK){
            fightResult = MONSTER_B_WON;
        }
        else if(monsterB.getType() == SCISSORS && monsterA.getType() == PAPER){
            fightResult = MONSTER_B_WON;
        }
        else{
            fightResult = DRAW;
        }

        if(isUsingReverse){
            if(fightResult == MONSTER_A_WON){
                fightResult = MONSTER_B_WON;
            }
            else if(fightResult == MONSTER_B_WON){
                fightResult = MONSTER_A_WON;
            }
        }

        return fightResult;
    }

    private void setAlreadyUsedMonsters(int monsterTypeA, int monsterTypeB){
        if(monsterTypeA == ROCK){
            playerA.getRockMonster().setCanUse(false);
        }
        else if(monsterTypeA == PAPER){
            playerA.getPaperMonster().setCanUse(false);
        }
        else{
            playerA.getScissorMonster().setCanUse(false);
        }

        if(monsterTypeB == ROCK){
            playerB.getRockMonster().setCanUse(false);
        }
        else if(monsterTypeB == PAPER){
            playerB.getPaperMonster().setCanUse(false);
        }
        else{
            playerB.getScissorMonster().setCanUse(false);
        }
    }

    private Monster getMonsterByType(int monsterType, boolean isFromPlayerA){
        if(isFromPlayerA){
            if(monsterType == ROCK){
                return playerA.getRockMonster();
            }
            else if(monsterType == PAPER){
                return playerA.getPaperMonster();        }
            else{
                return playerA.getScissorMonster();
            }
        }
        else{
            if(monsterType == ROCK){
                return playerB.getRockMonster();
            }
            else if(monsterType == PAPER){
                return playerB.getPaperMonster();        }
            else{
                return playerB.getScissorMonster();
            }
        }
    }

    public void resetMonstersAvailability(){
        playerA.getRockMonster().setCanUse(true);
        playerA.getPaperMonster().setCanUse(true);
        playerA.getScissorMonster().setCanUse(true);

        playerB.getRockMonster().setCanUse(true);
        playerB.getPaperMonster().setCanUse(true);
        playerB.getScissorMonster().setCanUse(true);
    }
}
