package dataaccess;

import business.Hashtag;
import java.io.*;
import java.util.*;
import business.Twit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class TwitRepo {
    public static long addRecord(Twit twit) throws IOException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query
                = "INSERT INTO twits (id, user_id, content, posted_date, mentioned_user_id)"
                + "VALUES (?, ?, ?, ?, ?)";
        
        String adjusted = "";

        for(String wordFromContent: twit.getContent().split(" ")){
//            System.out.println("word from content: " + wordFromContent + " end");
            if(wordFromContent.indexOf("#") == 0) {
                String hashtagContent = wordFromContent.substring(1);

//                Hashtag existingHashtag = HashtagRepo.get(hashtagContent);
//                System.out.println("existing hashtag from twit repo: " + existingHashtag);
//                if(existingHashtag == null) {
////                    Hashtag newHashtag = new Hashtag(1, hashtagContent);
////                    HashtagRepo.insert(newHashtag);
//                } else {
//                    HashtagRepo.update(new Hashtag(existingHashtag.getId(),
//                            existingHashtag.getCount()+1, hashtagContent));
//                }
                
                wordFromContent = "<a href=\"/MyTwitter/twit?q=" + hashtagContent +
                    "\" style=\"color:blue\">" + wordFromContent + "</a>";
            }
            if(wordFromContent.indexOf('@') == 0) {
                long userId = UserRepo.selectNickname(wordFromContent.substring(1));
                if(userId != 0) {
                    twit.setMentionedUserId(UserRepo.selectNickname(wordFromContent.substring(1)));
                    wordFromContent = "<span style=\"color:blue\">" + wordFromContent + "</span>";
                    System.out.println("word from content: " + wordFromContent);
                } else {
                    wordFromContent = "";
                }
            }
            adjusted += " " + wordFromContent;
        }

        System.out.println("adjusted stuff");
        System.out.println(adjusted);
        twit.setContent(adjusted);
        System.out.println("current twit in all method " + twit.toString());
        try {
            ps = connection.prepareStatement(query);
            ps.setLong(1, twit.getId());
            ps.setLong(2, twit.getUserId());
            ps.setString(3, twit.getContent());
            ps.setTimestamp(4, new Timestamp (twit.getPostedDate().getTime()));
            ps.setLong(5, twit.getMentionedUserId());

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
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ArrayList<Twit> twitList = new ArrayList<>();

        String query = "SELECT * FROM twits";
        try {
            ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                twitList.add(buildTwitFromResult(rs));
            }

            return twitList;
        } catch(SQLException e) {
            System.err.println(e);
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        return null;
    }

    public static long delete(long twitId) {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        String query = "DELETE FROM twits "
                + "WHERE id = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setLong(1, twitId);
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        return 0;
    }

    public static Twit buildTwitFromResult(ResultSet rs) throws SQLException {
        return new Twit(rs.getLong("id"),
            rs.getLong("user_id"),
            rs.getLong("mentioned_user_id"),
            rs.getString("content"),
            rs.getDate("posted_date"));
    }
}
