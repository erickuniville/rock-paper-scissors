package br.com.erick.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * Created by erick.budal on 12/11/2017.
 */
public abstract class AbstractDAO {

    protected String databaseFilePath = "C:\\Users\\erick\\Downloads\\rock_paper_scissor.db";

    protected void createTablesIfNotExists(){
        try {
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseFilePath);
            connection.setAutoCommit(false);

            String sql =
                    "CREATE TABLE IF NOT EXISTS Team(" +
                            "teamId INT NOT NULL PRIMARY KEY," +
                            "teamName TEXT);" +

                    "CREATE TABLE IF NOT EXISTS Monster(" +
                            "teamId INT NOT NULL ," +
                            "strength INT NOT NULL ," +
                            "defense INT NOT NULL ," +
                            "type INT NOT NULL ," +
                            "FOREIGN KEY(teamId) REFERENCES Team(teamId))";

            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);

            statement.close();
            connection.commit();
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
