package services;

import com.mysql.cj.jdbc.ServerPreparedStatement;

import java.sql.*;

/**
 * Klasa odpowiada za połączenie za bazą danych
 */
public class DatabaseService {

    /**
     * Ścieżka do bazy danych
     */
    private static final String dbPath = "jdbc:mysql://localhost:3306/calendar?serverTimezone=UTC&useSSL=false";

    private Connection con;
    private Statement stmt;
    private ResultSet rs;

    /**
     * Połączenie z bazą danych
     */
    public void connect()
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(dbPath, "root", "qwsaaswq");
            stmt = con.createStatement();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Rozłączenie z bazą danych
     */
    public void disconnect()
    {
        if (con != null)
        {
            try
            {
                con.close();
            }
            catch(Exception e) {}
        }
    }

    /**
     * Wykonuje zapytanie do bazy danych i zwraca wynik
     * @param sqlQuery zapytanie do bazy danych
     * @return wynik zapytania
     */
    public ResultSet executeQuery(String sqlQuery){
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = con.prepareStatement(sqlQuery);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    /**
     * Aktualizuje aktualizację do bazy danych
     * @param sqlQuery zapytanie do bazy danych
     */
    public void executeUpdate(String sqlQuery){
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = con.prepareStatement(sqlQuery);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Resetuje bazę danych, tworzy odpowiednią tabelę
     */
    public void resetDatabase(){
        String drop = "DROP TABLE Events;";
        String create = "CREATE TABLE Events(event_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, title VARCHAR(100), description VARCHAR(250), date Datetime, alertBefore INTEGER, place VARCHAR(30));";
        try {
            stmt.executeUpdate(drop);
            stmt.executeUpdate(create);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
