package dataaccess;

import java.io.*;
import java.util.*;
import business.Twit;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class TwitDB {
    
    final static String FILE_NAME = "/Users/batesjernigan/Desktop/code/Java/web_java/MyTwitter/web/tweets.txt";
    
    public static long addRecord(Twit twit) throws IOException {
        System.out.println("in twitdb add record");        
        
        File file = new File(FILE_NAME);
        try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file, true)))) {
            
            System.out.println("twit: " + twit.toString());
            out.println(twit.getEmail() + "|"
                    + twit.getDate() + "|"
                    + twit.getText());
            out.close();
            return 1;
        } catch(IOException e) {
            return 0;
        }
    }

    public static ArrayList<Twit> all() throws IOException {
        String line;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));
            ArrayList<Twit> twitList = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                System.out.println("first line: " + line);
                String[] twitAttribute = line.split(Pattern.quote("|"));
                twitList.add(new Twit(twitAttribute[0], twitAttribute[1], twitAttribute[2]));
            }
            
            return twitList;
        } catch (FileNotFoundException e) {
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, e);
        } catch (IOException e) {
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

}
