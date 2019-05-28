package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseService {

    private static final String dbPath = "jdbc:mysql://localhost:3306/calendar?serverTimezone=UTC&useSSL=false";

    private Connection con;
    private Statement stmt;
    private ResultSet rs;

    public void connect()
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(dbPath, "root", "qwsaaswq");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void disconnect()
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
}
