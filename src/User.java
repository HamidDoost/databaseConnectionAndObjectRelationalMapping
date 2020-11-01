import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

import org.sqlite.SQLiteDataSource;

public class User {
    public static void main(String[] args) {
        SQLiteDataSource ds = null;
        try {
            ds = new SQLiteDataSource();
            ds.setUrl("jdbc:sqlite:user.db");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        try {
            Connection connection = ds.getConnection();
            Statement statement = connection.createStatement();

            String sql = "create table if not exists User(" +
            			 "UserID int primary key, " +
            			 "UserFirstname varchar(50), " +
            			 "UserLastname varchar(50), " +
            			 "UserAddress varchar(50), " + 
            			 "UserGender varchar(10), " + 
            			 "UserEmail varchar(50) " +
            			 ")";
            int retVal = statement.executeUpdate(sql);

            // Initialise the data in the table
            // Clean out any pre-existing data
            sql = "delete from User";
            int rowsUpdated = statement.executeUpdate(sql);
            // Populate with new data
            PopulateDatabase(ds);

            // Insert some data
            sql = "insert into User(UserID, UserFirstname, UserLastname, " +
            	  "UserAddress, UserGender, UserEmail) " +
            	  "values(10,'Hamid', 'Doost','Pacific Hwy','male', 'hd@hamid.com') ";
            rowsUpdated = statement.executeUpdate(sql);

            // Update some data
             sql = "update User " + 
                   "set UserEmail = '14129670@student.uts.edu.au' " + 
                   "where UserID = 10";
            rowsUpdated = statement.executeUpdate(sql);

            // Delete some data
            sql = "delete from User " + 
            	  "where UserFirstname = 'William' ";
            rowsUpdated = statement.executeUpdate(sql);

            // Select all the rows from the User table and display them
            ResultSet results = statement.executeQuery("SELECT * FROM User");
            int UserId = 0;
            String UserFirstname = "";
            String UserLastname = "";
            String UserAddress = "";
            String UserGender = "";
            String UserEmail = "";
            System.out.println("Id\tFirstname\tLastname\tAddress\t	Gender\tEmail");
            while (results.next()) {
                UserId = results.getInt("UserId");
                UserFirstname = results.getString("UserFirstname");
                UserLastname = results.getString("UserLastname");
                UserAddress = results.getString("UserAddress");
                UserGender = results.getString("UserGender");
                UserEmail = results.getString("UserEmail");
                System.out.println(UserId + "\t" + UserFirstname + "\t"+"\t" + UserLastname + "\t"+"\t" + UserAddress +"\t" + UserGender + "\t"+ UserEmail + " \t");
            }
            results.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Inserts a single User into the database
     * 
     * @param ds            The database
     * @param UserId        The User Id
     * @param UserFirstname The Firstname of the User
     * @param UserLastname  The Lastname of the User
     * @param UserAddress   The address of the User
     * @param UserGender    The gender of the User
     * @param UserEmail     The Email of the User
     */

    public static void InsertUser(SQLiteDataSource ds, int UserId, String UserFirstname, String UserLastname,
            String UserAddress, String UserGender, String UserEmail) {
        String sql = "insert into User(UserID, UserFirstname, UserLastname, " + 
        			 "UserAddress, UserGender, UserEmail) " +
        			 "values(" + UserId + ",'" + UserFirstname + "','" + UserLastname + "','" + UserAddress + "','" + UserGender + "','" + UserEmail + "') ";
        try {
            Connection connection = ds.getConnection();
            Statement statement = connection.createStatement();
            int rowsUpdated = statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void PopulateDatabase(SQLiteDataSource ds) {
        InsertUser(ds, 1, "ali" , "lakemba" , "Broughton st." , "female" , "ali@yahoo.com" );
        InsertUser(ds, 2, "John" , "Smith" , "Albert st." , "male" , "john@gmail.com" );
        InsertUser(ds, 3, "Mary" , "Turner" , "Pennat st." , "female" , "Mary@gmail.com" );
        InsertUser(ds, 4, "William" , "Brook" , "White st." , "male" , "William@yahoo.com" );
        

    }
}
