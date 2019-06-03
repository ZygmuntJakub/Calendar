package services;

import java.sql.*;

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
            stmt = con.createStatement();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

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

    public Statement getStmt() {
        return stmt;
    }

    public void setStmt(Statement stmt) {
        this.stmt = stmt;
    }

    public ResultSet getRs() {
        return rs;
    }

    public void setRs(ResultSet rs) {
        this.rs = rs;
    }

    public ResultSet executeQuery(String sqlQuery){
        ResultSet resultSet = null;
        try {
            resultSet = stmt.executeQuery(sqlQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }
}
