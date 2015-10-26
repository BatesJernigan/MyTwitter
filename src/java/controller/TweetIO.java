package controller;

import java.io.*;
import java.util.*;
import java.util.Date;
import business.Tweets;

public class TweetIO {

    public static void addRecord(Tweets tweet, String filename) throws IOException {
        File file = new File(filename);
        PrintWriter out = new PrintWriter(
                new FileWriter(file, true));
        out.println(tweet.getEmail() + "|"
                + tweet.getText() + "|"
                + tweet.getDate());
        out.close();
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

    public static ArrayList<Tweets> getEntrys(String filename) throws IOException {
        ArrayList<Tweets> tweets = new ArrayList<Tweets>();
        BufferedReader in = new BufferedReader(
                new FileReader(filename));
        String line = in.readLine();
        while (line != null) {
            try {
                StringTokenizer t = new StringTokenizer(line, "|");
                String email = t.nextToken();
                String text = t.nextToken();
                String date = t.nextToken();
                Tweets tweet = new Tweets(email, date, text);
                tweets.add(tweet);
                line = in.readLine();
            } catch (NoSuchElementException e) {
                line = in.readLine();
            }
        }
        in.close();
        return tweets;
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
