package com.revature.util;

import com.revature.models.User;

import java.io.*;

public class FileDB {


    /*
     * When the app starts, populate this list with Users.
     * When it shuts down, populate a linked file.
     */
    public static LinkedList<User> userList = new LinkedList<>();
    public static final String CSV_FILE_PATH = "src/main/resources/users.csv";

    public static void startup() throws IOException {
        // Set up the BufferedReader object, point it to the correct filepath.
        BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE_PATH));

        // Read each line and parse it into a User object.
        String line = reader.readLine();
        while (line != null && !line.equals("")) {
            // For each line read from the file, convert it into a User object.
            User user = parseUser(line);
            // Add it to userList.
            userList.add(user);
            // Read the next line.
            line = reader.readLine();
        }
    }

    private static User parseUser(String csv) {
        User user = new User();

        // Split the CSV file into tokens at each comma.
        String[] tokens = csv.split(",");

        // Set the fields of the User object in order, using the tokens' indices in the array.
        user.setUsername(tokens[0]);
        user.setPassword(tokens[1]);
        user.setHasChecking(Boolean.parseBoolean(tokens[2]));
        user.setHasSavings(Boolean.parseBoolean(tokens[3]));

        return user;
    }

    public static void shutdown() throws IOException {
        // Set up the BufferedWriter object, point it to the correct filepath.
        BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE_PATH));

        // Convert each User into a string of CSV text and write it to the file.
        for (int i = 0; i < userList.getSize(); i++) {
            writer.write(parseCSV(userList.get(i)));
        }
        writer.close();
    }

    private static String parseCSV(User user) {
        return user.getUsername() + ","
               + user.getPassword() + ","
               + user.isHasChecking() + ","
               + user.isHasSavings() + "\n";
    }

}