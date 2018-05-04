package model;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database_Manager {

    public static final String SQLUSERNAME = "root";   //IMPORTANT:: Change to configuration
    public static final String SQLPASSWORD = "root";   //file in real use for security
    public static final String DATABASELOC = "easy_rider_database";
    private final Connection conn;

    public Database_Manager() {
        this.conn = connectToDatabase();
    }

    public boolean verifyAccount(String username, String password) {
        try {
            String sql = "SELECT * FROM Account_Info WHERE username = ? AND password = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet rs = preparedStatement.executeQuery();

            //If Query Returned Result, User is Found
            return rs.next();

        } catch (SQLException ex) {
            Logger.getLogger(Database_Manager.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean addAccount(String firstName, String lastName, String username, String password) {
        try {
            String sql = "INSERT INTO Account_Info (firstName, lastName, username, password)"
                    + "VALUES (?, ?, ?, ?)";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, username);
            preparedStatement.setString(4, password);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Database_Manager.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public String listAccounts() {
        try {
            String queryResults = "";
            String sql = "SELECT * FROM Account_Info";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery(sql);

            //Get Query Results
            while (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                String firstName = rs.getString("firstname");
                String lastName = rs.getString("lastname");
                queryResults += "Username: " + username + "\nPassword: " + password
                        + "\nFirst Name: " + firstName + "\nLast Name: " + lastName;
            }

            return queryResults;

        } catch (SQLException ex) {
            Logger.getLogger(Database_Manager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public boolean saveFile(String user, String fileName, String dataFile) {
        try {
            String sql = "INSERT INTO R_File (file_owner, file_name, data_file, last_modified)"
                    + "VALUES (?, ?, ?, ?)";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, user);
            preparedStatement.setString(2, fileName);
            preparedStatement.setString(3, dataFile);
            preparedStatement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            System.out.println(System.currentTimeMillis());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Database_Manager.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public String listUserFiles(String user) {
        try {
            String queryResults = null;
            String sql = "SELECT * FROM R_File WHERE file_owner = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, user);
            ResultSet rs = preparedStatement.executeQuery();
            
            //Get Query Results
            while (rs.next()) {
                String filename = rs.getString("file_name");
                String date = rs.getTimestamp("last_modified").toString();
                
                //Display File List Result in JavaScript Table
                if(queryResults == null){
                    queryResults = filename + "," + date;
                }else{
                    queryResults += "," + filename + "," + date;
                }
            }
            return queryResults;
        } catch (SQLException ex) {
            Logger.getLogger(Database_Manager.class.getName()).log(Level.SEVERE, null, ex);
            return ex.toString();
        }
    }
    
    public String listUserFilesDateSort(String user) {
        try {
            String queryResults = null;
            String sql = "SELECT * FROM R_File WHERE file_owner = ? ORDER BY last_modified";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, user);
            ResultSet rs = preparedStatement.executeQuery();
            
            //Get Query Results
            while (rs.next()) {
                String filename = rs.getString("file_name");
                String date = rs.getTimestamp("last_modified").toString();
                
                //Display File List Result in JavaScript Table
                if(queryResults == null){
                    queryResults = filename + "," + date;
                }else{
                    queryResults += "," + filename + "," + date;
                }
            }
            return queryResults;
        } catch (SQLException ex) {
            Logger.getLogger(Database_Manager.class.getName()).log(Level.SEVERE, null, ex);
            return ex.toString();
        }
    }
    
    public String getUserFile(String user, String fileName){
        try {
            String data_file = "";
            String sql = "SELECT * FROM R_File WHERE file_owner = ? AND file_name = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, user);
            preparedStatement.setString(2, fileName);
            ResultSet rs = preparedStatement.executeQuery();
           
            //Get Query Results
            while (rs.next()) {
                data_file = rs.getString("data_file");
            }
            return data_file;
        } catch (SQLException ex) {
            Logger.getLogger(Database_Manager.class.getName()).log(Level.SEVERE, null, ex);
            return ex.toString();
        }
    }

    public final Connection connectToDatabase() {
        try {
            Connection databaseConnection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/easy_rider_database?autoReconnect=true&useSSL=false",
                    "root", "root");
            return databaseConnection;
        } catch (SQLException ex) {
            Logger.getLogger(Database_Manager.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
            return null;
        }
    }
}
