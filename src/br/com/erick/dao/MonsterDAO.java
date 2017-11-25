package br.com.erick.dao;

import br.com.erick.model.Monster;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by erick.budal on 12/11/2017.
 */
public class MonsterDAO extends AbstractDAO {
	
	public MonsterDAO() {
		createTablesIfNotExists();
	}

    public void insertTeamMonsters(Monster rockMonster, Monster paperMonster, Monster scissorsMonster, int teamId){
        try {
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseFilePath);
            connection.setAutoCommit(false);

            String sql = String.format("INSERT INTO Monster(teamId, strength, defense, type) VALUES" +
                                       "(?, ?, ?, ?)," +
                                       "(?, ?, ?, ?)," +
                                       "(?, ?, ?, ?)");

            PreparedStatement prepared = connection.prepareStatement(sql);
            prepared.setInt(1, teamId);
            prepared.setInt(2, rockMonster.getStrength());
            prepared.setInt(3, rockMonster.getDefense());
            prepared.setInt(4, rockMonster.getType());

            prepared.setInt(5, teamId);
            prepared.setInt(6, paperMonster.getStrength());
            prepared.setInt(7, paperMonster.getDefense());
            prepared.setInt(8, paperMonster.getType());

            prepared.setInt(9, teamId);
            prepared.setInt(10, scissorsMonster.getStrength());
            prepared.setInt(11, scissorsMonster.getDefense());
            prepared.setInt(12, scissorsMonster.getType());

            prepared.executeUpdate();

            prepared.close();
            connection.commit();
            connection.close();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public List<Monster> getMonstersByTeamId(int teamId){

        List<Monster> monsters = null;

        try {
            monsters = new Vector<>();

            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseFilePath);
            connection.setAutoCommit(false);

            PreparedStatement prepared = connection.prepareStatement("SELECT * FROM Monster WHERE teamId = ?;");
            prepared.setInt(1, teamId);
            ResultSet resultSet = prepared.executeQuery();

            while (resultSet.next()) {
                int strength = resultSet.getInt("strength");
                int defense = resultSet.getInt("defense");
                int type = resultSet.getInt("type");

                monsters.add(new Monster(strength, defense, type, teamId));
            }

            resultSet.close();
            prepared.close();
            connection.commit();
            connection.close();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        return monsters;
    }
}
