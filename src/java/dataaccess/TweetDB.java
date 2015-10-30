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
        // PASTE ABSOLUTE PATH HERE
//        System.out.println(user.getEmail() + "|"
//                + user.getPassword() + "|"
//                + user.getFullName() + "|"
//                + user.getNickname() + "|"
//                + user.getBirthdate());
//        try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file, true)))) {
//            out.println(user.getEmail() + "|"
//                + user.getPassword() + "|"
//                + user.getFullName() + "|"
//                + user.getNickname() + "|"
//                + user.getBirthdate());
//            out.close();
//            return 0;
//        } catch(IOException e) {
//            return 1;
//        }
        
        
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

    //public static Tweets getEntry(String user, String filename) throws IOException {
    //    File file = new File(filename);
    //    BufferedReader in = new BufferedReader(
    //            new FileReader(file));
    //    Tweets tweet = new Tweets();
    //    String line = in.readLine();
    //    while (line != null) {
    //        StringTokenizer t = new StringTokenizer(line, "|");
    //        String email = t.nextToken();
    //        if (email.equalsIgnoreCase(user)) {
    //            String firstName = t.nextToken();
    //            String lastName = t.nextToken();
    //            tweet.setUser(user);
    //            tweet.set(firstName);
    //            tweet.setLastName(lastName);
    //        }
    //        line = in.readLine();
    //    }
    //    in.close();
    //    return tweet;
    //} 

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

    //public static HashMap<String, Tweets> getUsersMap(String filename) throws IOException {
    //    HashMap<String, Tweets> tweets = new HashMap<String, Tweets>();
    //    BufferedReader in = new BufferedReader(
    //            new FileReader(filename));
    //    String line = in.readLine();
    //    while (line != null) {
    //        try {
    //            StringTokenizer t = new StringTokenizer(line, "|");
    //            int weight = Integer.parseInt(t.nextToken());
    //            String location = t.nextToken();
    //            String type = t.nextToken();
    //            int entered = Integer.parseInt(t.nextToken());
    //            String user = t.nextToken();
    //            Tweets tweet = new Tweets(weight, location, type, entered, user);
    //            tweets.put(emailAddress, tweet);
    //            line = in.readLine();
    //        } catch (NoSuchElementException e) {
    //            line = in.readLine();
    //        }
    //    }
    //    in.close();
    //    return tweets;
    //}
}
