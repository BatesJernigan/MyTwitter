package dataaccess;

import java.io.*;
import java.util.*;
import business.Twit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class TwitRepo {
    
//    final static String FILE_NAME = "/Users/batesjernigan/Desktop/code/Java/web_java/MyTwitter/web/tweets.txt";
    
    public static long addRecord(Twit twit) throws IOException {
        
        ConnectionPool pool = ConnectionPool.getInstance();
        System.out.println("pool " + pool.toString());
        Connection connection = pool.getConnection();
        System.out.println("connection " + connection.toString());
        PreparedStatement ps = null;

//        private String email, text, date;
        String query
                = "INSERT INTO twits (email, text, date) "
                + "VALUES (?, ?, ?)";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, twit.getEmail());
            ps.setString(2, twit.getContent());
            ps.setDate(3, new java.sql.Date(twit.getPostedDate().getTime()));

            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            return 0;
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static ArrayList<Twit> all() throws IOException {
        String line;
//        try {
//            BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));
//            ArrayList<Twit> twitList = new ArrayList<>();
//            while ((line = reader.readLine()) != null) {
//                System.out.println("first line: " + line);
//                String[] twitAttribute = line.split(Pattern.quote("|"));
//                twitList.add(new Twit(twitAttribute[0], twitAttribute[1], twitAttribute[2]));
//            }
//            
//            return twitList;
//        } catch (FileNotFoundException e) {
//            Logger.getLogger(UserRepo.class.getName()).log(Level.SEVERE, null, e);
//        } catch (IOException e) {
//            Logger.getLogger(UserRepo.class.getName()).log(Level.SEVERE, null, e);
//        }
        return null;
    }

}
