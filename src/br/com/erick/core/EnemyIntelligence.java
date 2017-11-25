package br.com.erick.core;

import br.com.erick.model.Monster;

import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

import static br.com.erick.constants.Constants.PAPER;
import static br.com.erick.constants.Constants.ROCK;
import static br.com.erick.constants.Constants.SCISSORS;
import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * Created by erick.budal on 12/11/2017.
 */
public class EnemyIntelligence {

    private HashMap<Integer, Boolean> monstersAvailability = new HashMap<>();
    private boolean reverseAvailable = true;

    public EnemyIntelligence(){
        monstersAvailability.put(ROCK, true);
        monstersAvailability.put(PAPER, true);
        monstersAvailability.put(SCISSORS, true);
    }

    public boolean isReverseAvailable() {
        return reverseAvailable;
    }

    public void setReverseAvailable(boolean reverseAvailable) {
        this.reverseAvailable = reverseAvailable;
    }

    public int chooseMonster(){
        int randomMonsterType = current().nextInt(0, 3);

        while(!monstersAvailability.get(randomMonsterType)){
            randomMonsterType = current().nextInt(0, 3);
        }

        monstersAvailability.put(randomMonsterType, false);

        return randomMonsterType;
    }

    public boolean chooseIfUseReverse(){
        if(reverseAvailable){
            boolean useReverse = current().nextBoolean();
            return useReverse;
        }
        else{
            return false;
        }
    }

    public static Monster generateMonster(int type){
        int randomStrength = ThreadLocalRandom.current().nextInt(1, 100);
        int randomDefense = 100 - randomStrength;

        return new Monster(randomStrength, randomDefense, type);
    }
}
