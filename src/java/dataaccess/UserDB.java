/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;
import business.User;
import java.io.*;

/**
 *
 * @author xl
 */
public class UserDB {
    public static long insert(User user) throws IOException {
        System.out.println("Working Directory = " +
            System.getProperty("user.dir"));
        
        // PASTE ABSOLUTE PATH HERE
        File file = new File("/Users/batesjernigan/Desktop/code/Java/web_java/MyTwitter/web/database.txt");
        System.out.println(user.getFullName() + "|"
                + user.getEmail() + "|"
                + user.getPassword() + "|"
                + user.getNickname() + "|"
                + user.getBirthdate());
        try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file, true)))) {
            out.println(user.getFullName() + "|"
                + user.getEmail() + "|"
                + user.getPassword() + "|"
                + user.getNickname() + "|"
                + user.getBirthdate());
            out.close();
            return 0;
        } catch(IOException e) {
            System.out.println("ERROR: " + e);
            System.out.println("there was an error");
            return 1;
        }
    }
    public static User select(String emailAddress)
    {
        //search in the database.txt and find the User, if does not exist return null; if exist make a User object and return it.
        return null;
    }
    
}
