package DatabaseHandler;

import Config.config;
import Exeption.Checker;
import org.postgresql.util.PSQLException;

import java.sql.*;

public class DatabaseHandler {

    private static String url = "jdbc:postgresql://"+ config.DBIP+"/"+config.DBname;
    private static String user = config.DBuser;
    private static String password = config.DBpw;
    private Connection connection = null;
    private String vin;


    public void connectToDB (String vin1){
        vin = vin1;
        Connection conn = null;
        this.connection = conn;
        try{
            this.connection = DriverManager.getConnection(url, user, password);
            System.out.println("Successfully connected to DB ");
            createTable(vin);
        } catch (PSQLException ex){
            System.out.println("Table does not exist.");
        }
        catch(SQLException e){
            System.out.println("Fehler bei connectToDB.");
        }

    }

    public void disconnect(){
        try {
            this.connection.close();
            Checker.setDBConnected(false);
            System.out.println("Connection to the DB closed.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTable(String tableName){
        String sqlStatement = "CREATE TABLE IF NOT EXISTS " + tableName + "(Date_Time TIMESTAMP PRIMARY KEY, value double precision, parameter varchar(24));";
        try {
            PreparedStatement ps = this.connection.prepareStatement(sqlStatement);
            ps.executeUpdate();
            ps.close();
            System.out.println("Table "+ tableName +" created.");
            //System.out.println(sqlStatement);
        } catch (Exception e) {
           System.out.println("Fehler bei Tabellenerzeugung.");
           System.out.println(e.getMessage());
        }
    }



    public void insertData ( String type, double value){

        String time = "'" + getCurrentTimeStamp().toString() + "'";
        String typ = "'" + type + "'";;
        Double wert = value;

        String sql="INSERT INTO "+ vin + "(date_time, value, parameter) VALUES("+ time  + ", " + wert +", "+ typ +" );";
        System.out.println(sql);
        try {
            PreparedStatement sth = this.connection.prepareStatement(sql);
            sth.executeUpdate();
            System.out.println("Data successfully inserted");
        } catch (SQLException e) {
            System.out.println("Fehler bei Insert von Daten.");
        }


    }

    private static java.sql.Timestamp getCurrentTimeStamp() {

        java.util.Date today = new java.util.Date();
        return new java.sql.Timestamp(today.getTime());

    }
}
