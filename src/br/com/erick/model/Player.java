package br.com.erick.model;

/**
 * Created by erick.budal on 10/10/2017.
 */
public class Player {
    private String name;
    private int lifePoints;
    private Monster rockMonster;
    private Monster paperMonster;
    private Monster scissorMonster;

    public Player(String name, int lifePoints, Monster rockMonster, Monster paperMonster, Monster scissorMonster) {
        this.name = name;
        this.lifePoints = lifePoints;
        this.rockMonster = rockMonster;
        this.paperMonster = paperMonster;
        this.scissorMonster = scissorMonster;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getLifePoints() {
        return lifePoints;
    }
    public void setLifePoints(int lifePoints) {
        this.lifePoints = lifePoints;
    }
    public Monster getRockMonster() {
        return rockMonster;
    }
    public void setRockMonster(Monster rockMonster) {
        this.rockMonster = rockMonster;
    }
    public Monster getPaperMonster() {
        return paperMonster;
    }
    public void setPaperMonster(Monster paperMonster) {
        this.paperMonster = paperMonster;
    }
    public Monster getScissorMonster() {
        return scissorMonster;
    }
    public void setScissorMonster(Monster scissorMonster) {
        this.scissorMonster = scissorMonster;
    }
}
