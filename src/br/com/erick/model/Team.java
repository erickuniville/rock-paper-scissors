package br.com.erick.model;

/**
 * Created by erick.budal on 12/11/2017.
 */
public class Team {

    public int teamId;
    public String teamName;
    private Monster rockMonster;
    private Monster paperMonster;
    private Monster scissorMonster;

    public Team(int teamId, String teamName, Monster rockMonster, Monster paperMonster, Monster scissorMonster) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.rockMonster = rockMonster;
        this.paperMonster = paperMonster;
        this.scissorMonster = scissorMonster;
    }

    public Team(){

    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
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
