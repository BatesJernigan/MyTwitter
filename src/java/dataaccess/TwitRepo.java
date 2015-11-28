package dataaccess;

import java.io.*;
import java.util.*;
import business.Twit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class TwitRepo {
    
//    final static String FILE_NAME = "/Users/batesjernigan/Desktop/code/Java/web_java/MyTwitter/web/tweets.txt";
    
    public static long addRecord(Twit twit) throws IOException {
        
        ConnectionPool pool = ConnectionPool.getInstance();
        System.out.println("pool " + pool.toString());
        Connection connection = pool.getConnection();
        System.out.println("connection " + connection.toString());
        PreparedStatement ps = null;

        String query
                = "INSERT INTO twits (id, user_id, content, posted_date) "
                + "VALUES (?, ?, ?, ?)";
        try {
            ps = connection.prepareStatement(query);
            ps.setLong(1, twit.getId());
            ps.setLong(2, twit.getUserId());
            ps.setString(3, twit.getContent());
            ps.setTimestamp(4, new Timestamp (twit.getPostedDate().getTime()));

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
        System.out.println("in twit all");
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ArrayList<Twit> twitList = new ArrayList<>();

        String query = "SELECT * FROM twits";
        System.out.println("in twit all");
        try {
            System.out.println("start of try");
            System.out.println(ps);
            ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            
            System.out.println("right before while");
            while (rs.next()) {
                System.out.println("inside of while");
                Twit currentTwit = buildTwitFromResult(rs);
                StringBuilder newContent;
//                twitList.add(currentTwit);
//                String adjusted = "";
//                
//                for (String wordFromContent: currentTwit.getContent().split(" ")){
//                    if(wordFromContent.indexOf("@") == 0 || wordFromContent.indexOf("#") == 0){
//                        wordFromContent = "<span style=\"color:blue\">" + wordFromContent + "</span>";
//                    }
//                    adjusted += " " + wordFromContent; 
//                    
//                }
//                System.out.println("adjusted stuff");
//                System.out.println(adjusted);
//                currentTwit.setContent(adjusted);
                twitList.add(currentTwit);
            }
            System.out.println("twit list from all");

            return twitList;
        } catch(SQLException e) {
            System.out.println("THERE WAS AN ERROR");
            System.err.println(e);
        } finally {
            System.out.println("in finally");
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        return null;
    }
    
    private static Twit buildTwitFromResult(ResultSet rs) throws SQLException {
        return new Twit(rs.getLong("id"),
                rs.getLong("user_id"),
                rs.getDate("posted_date"),
                rs.getString("content"));
    }
}
