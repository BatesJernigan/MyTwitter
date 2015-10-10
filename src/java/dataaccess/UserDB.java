/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;
import business.User;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 *
 * @author xl
 */
public class UserDB {
    
    static final String FILE_PATH = "/Users/batesjernigan/Desktop/code/Java/web_java/MyTwitter/web/database.txt";
    static final String DATE_FORMAT = "M/d/yyyy";
    public static long insert(User user) throws IOException {
        // PASTE ABSOLUTE PATH HERE
        File file = new File(FILE_PATH);
        System.out.println(user.getEmail() + "|"
                + user.getPassword() + "|"
                + user.getFullName() + "|"
                + user.getNickname() + "|"
                + user.getBirthdate());
        try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file, true)))) {
            out.println(user.getEmail() + "|"
                + user.getPassword() + "|"
                + user.getFullName() + "|"
                + user.getNickname() + "|"
                + user.getBirthdate());
            out.close();
            return 0;
        } catch(IOException e) {
            return 1;
        }
    }
    
    // BROKEN
    public static User select(String emailAddress, String password) {
        DateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT);
        BufferedReader reader;
        try {
            String line;
            reader = new BufferedReader(new FileReader(FILE_PATH));
            while ((line = reader.readLine()) != null) {
                String[] userAttribute = line.split(Pattern.quote("|"));
                if (userAttribute[0].equals(emailAddress) &&
                        userAttribute[1].equals(password)) {
                    return new User(userAttribute[0], userAttribute[1], 
                            userAttribute[2], userAttribute[3],
                            userAttribute[4]);
                }
            }
        } catch (FileNotFoundException e) {
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, e);
        } catch (IOException e) {
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }
    
    // BROKEN
    //search in the database.txt and find the User, if does not exist
    // return null; if exist make a User object and return it.
    public static User search(String emailAddress) {
        BufferedReader reader;
        String line;
        try {
            reader = new BufferedReader(new FileReader(FILE_PATH));
            while ((line = reader.readLine()) != null) {
                System.out.println("first line: " + line);
                String[] userAttribute = line.split(Pattern.quote("|"));
                if (userAttribute[0].equals(emailAddress)) {
                    return new User(userAttribute[0], userAttribute[1], 
                            userAttribute[2], userAttribute[3],
                            userAttribute[4]);
                }
            }
        } catch (FileNotFoundException e) {
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, e);
        } catch (IOException e) {
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }
}
