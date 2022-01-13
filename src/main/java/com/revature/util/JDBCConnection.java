package com.revature.util;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCConnection {

   /*
    * We're going to create a similar idea to what is called a singleton.
    * A singleton is a design pattern in which you only ever want one instance
    * of the object to exist.
    *
    * You prevent additional object creation by privatizing your constructor
    * and creating a public method that controls when (if ever) a new object
    * is created.
    *
    * For this example, we're not going to privatize the class, just the conn object.
    */

    // Import a java.sql.Connection object from JDBC.
    private static Connection conn = null;

    public static Connection getConnection() {

       /*
        * Responsible for establishing a new connection if one doesn't exist.
        * Otherwise, return the open connection.
        *
        * Credentials required: endpoint URL, master username/password.
        * We don't want to code these values into the app itself! Store them externally.
        * - Example: Store in a file (that is added to .gitignore) or OS environment variables.
        */

        if (conn == null) {
            // Establish a new connection. Load endpoint address, username and PW from a file.
            Properties props = new Properties();

            try {
                /*
                 * Load our file (in main/resources) regardless of its filepath.
                 * Maven (or any dependency manager) may change the file structure when packaging the app.
                 * This approach uses reflection and loads as an input stream.
                 * An input stream is suitable input for Properties.load() (using getResourceAsStream()).
                 */
                props.load(JDBCConnection.class.getClassLoader().getResourceAsStream("connection.properties"));

                String endpoint = props.getProperty("endpoint");
                String username = props.getProperty("username");
                String password = props.getProperty("password");
                // URL format (JDBC) - jdbc:postgresql://[endpoint]/[database]
                String url = "jdbc:postgresql://" + endpoint + "/postgres";

                conn = DriverManager.getConnection(url, username, password);

            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
            return conn;
        }
        return conn;

    }

}
