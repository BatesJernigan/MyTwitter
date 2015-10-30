package dataaccess;

import java.io.*;
import java.util.*;
import business.Tweets;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class TweetDB {
    
    final static String FILE_NAME = "/Users/batesjernigan/Desktop/code/Java/web_java/MyTwitter/web/tweets.txt";
    
    public static long addRecord(Tweets tweet) throws IOException {
        System.out.println("in tweetdb add record");        
        
        File file = new File(FILE_NAME);
        try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file, true)))) {
            
            System.out.println("tweet: " + tweet.toString());
            out.println(tweet.getEmail() + "|"
                    + tweet.getDate() + "|"
                    + tweet.getText());
            out.close();
            return 1;
        } catch(IOException e) {
            return 0;
        }
    }

    public static ArrayList<Tweets> all() throws IOException {
        String line;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));
            ArrayList<Tweets> tweetList = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                System.out.println("first line: " + line);
                String[] tweetAttribute = line.split(Pattern.quote("|"));
                tweetList.add(new Tweets(tweetAttribute[0], tweetAttribute[1], tweetAttribute[2]));
            }
            
            return tweetList;
        } catch (FileNotFoundException e) {
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, e);
        } catch (IOException e) {
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

}
