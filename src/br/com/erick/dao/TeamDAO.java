package br.com.erick.dao;

import br.com.erick.constants.Constants;
import br.com.erick.model.Monster;
import br.com.erick.model.Team;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Vector;

import static br.com.erick.constants.Constants.PAPER;
import static br.com.erick.constants.Constants.ROCK;
import static br.com.erick.constants.Constants.SCISSORS;

/**
 * Created by erick.budal on 12/11/2017.
 */
public class TeamDAO extends AbstractDAO {

    public TeamDAO(){
        createTablesIfNotExists();
    }

    public List<Team> getTeams(){
        List<Team> teams = null;

        try {
            teams = new Vector<>();

            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseFilePath);
            connection.setAutoCommit(false);

            PreparedStatement prepared = connection.prepareStatement("SELECT * FROM Team;");
            ResultSet resultSet = prepared.executeQuery();

            while (resultSet.next()) {
                Team actualTeam = new Team();

                int teamId = resultSet.getInt("teamId");
                String teamName = resultSet.getString("teamName");
                MonsterDAO monsterDAO = new MonsterDAO();
                List<Monster> monsters = monsterDAO.getMonstersByTeamId(teamId);
                for(Monster monster : monsters){
                    switch (monster.getType()){
                        case ROCK:
                            actualTeam.setRockMonster(monster);
                            break;
                        case PAPER:
                            actualTeam.setPaperMonster(monster);
                            break;
                        case SCISSORS:
                            actualTeam.setScissorMonster(monster);
                            break;
                    }
                }
                actualTeam.setTeamId(teamId);
                actualTeam.setTeamName(teamName);
                teams.add(actualTeam);
            }

            resultSet.close();
            prepared.close();
            connection.commit();
            connection.close();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        return teams;
    }

    public int insertTeam(String teamName){

        int teamId = Integer.MIN_VALUE;

        try {
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseFilePath);
            connection.setAutoCommit(false);

            //AUTO_INCREMENT implementation
            PreparedStatement prepared = connection.prepareStatement("SELECT teamId FROM Team ORDER BY teamId DESC LIMIT 1;");
            ResultSet resultSet = prepared.executeQuery();
            try {
                teamId = resultSet.getInt("teamId") + 1;
            }
            catch(Exception ex){
                teamId = 1;
            }
            resultSet.close();
            prepared.close();

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Team(teamId, teamName) VALUES (?, ?)");
            preparedStatement.setInt(1, teamId);
            preparedStatement.setString(2, teamName);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.commit();
            connection.close();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        return teamId;
    }

    private int getNextId(Connection connection){

        int nextId = -1;

        try {
            Class.forName("org.sqlite.JDBC");

            PreparedStatement prepared = connection.prepareStatement("SELECT teamId FROM Team ORDER BY teamId DESC LIMIT 1;");
            ResultSet resultSet = prepared.executeQuery();

            nextId = resultSet.getInt("teamId") + 1;

            resultSet.close();
            prepared.close();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        return nextId;
    }

}
