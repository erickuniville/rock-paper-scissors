package br.com.erick.model;

/**
 * Created by erick.budal on 10/10/2017.
 */
public class Monster {
    private int strength;
    private int defense;
    private int type;
    private int teamId;
    private boolean canUse = true;

    public Monster(int strength, int defense, int type) {
        this.strength = strength;
        this.defense = defense;
        this.type = type;
    }

    public Monster(int strength, int defense, int type, int teamId) {
        this.strength = strength;
        this.defense = defense;
        this.type = type;
    }

    public int getStrength() {
        return strength;
    }
    public void setStrength(int strength) {
        this.strength = strength;
    }
    public int getDefense() {
        return defense;
    }
    public void setDefense(int defense) {
        this.defense = defense;
    }
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public boolean isCanUse() {
        return canUse;
    }
    public void setCanUse(boolean canUse) {
        this.canUse = canUse;
    }
    public int getTeamId() {
        return teamId;
    }
    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }
}
