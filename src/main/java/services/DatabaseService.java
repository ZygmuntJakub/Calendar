package services;

import java.sql.*;
import java.util.Calendar;

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
    private PreparedStatement preparedStatement;

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
                if(preparedStatement != null){
                    preparedStatement.close();
                }
                if(stmt != null){
                    stmt.close();
                }
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
     *
     * @param title tytuł wydarzenia
     * @param desc opis wydarzenia
     * @param date data wydarzenia
     * @param place miejsce wydarzenia
     * @param alert czas dla powiadomienia
     */
    public void executePreparedUpdate(String title, String desc, Calendar date, String place, Integer alert){

        try {
            preparedStatement = con.prepareStatement("INSERT INTO Events(title, description, date, alertBefore, place) VALUES (?,?,?,?,?);");
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, desc);
            preparedStatement.setTimestamp(3, new Timestamp(date.getTimeInMillis()));
            preparedStatement.setInt(4, alert);
            preparedStatement.setString(5,place);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Resetuje bazę danych, tworzy odpowiednią tabelę
     * DELETE FROM Events;
     */
    public void resetDatabase(){
        //String drop = "DROP TABLE Events;";
        //String create = "CREATE TABLE Events(event_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, title VARCHAR(100), description VARCHAR(250), date Datetime, alertBefore INTEGER, place VARCHAR(30));";
        String delete = "DELETE FROM Events;";
        try {
//            stmt.executeUpdate(drop);
//            stmt.executeUpdate(create);
            stmt.executeUpdate(delete);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
