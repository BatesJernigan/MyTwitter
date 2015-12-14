/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;

import business.Hashtag;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author batesjernigan
 */
public class HashtagRepo {
    
    public static long insert(Hashtag hashtag) throws IOException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query
                = "INSERT INTO hashtags (id, count, content)"
                + "VALUES (?, ?, ?)";

        System.out.println("current hashtag in insert method " + hashtag.toString());
        try {
            ps = connection.prepareStatement(query);
            ps.setLong(1, hashtag.getId());
            ps.setLong(2, hashtag.getCount());
            ps.setString(3, hashtag.getContent());

            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            return 0;
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    public static long insertAll(ArrayList<Hashtag> hashtags) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query = "INSERT INTO hashtags (id, count, content) VALUES";

        try {
            int indexForInsert = 1;
            for(int i =0; i<hashtags.size(); i++) {
                if(hashtags.get(i).getCount() == 1) {
                    System.out.println("hashtags size: "+hashtags.size() +"; i: " + i);
                    System.out.println("current hashtag in insert method " + hashtags.get(i));
                    query += "(?, ?, ?)";
                    // checks whether or not to add a ,
                    if(i != hashtags.size()-1) {
                        System.out.println("about to add a ','");
                        query += ", ";
                    }
                }
            }
            ps = connection.prepareStatement(query);
            for(int i =0; i<hashtags.size(); i++) {
                System.out.println("current prepared statement pre sets: " + ps);
                ps.setLong(indexForInsert++, hashtags.get(i).getId());
                ps.setLong(indexForInsert++, hashtags.get(i).getCount());
                ps.setString(indexForInsert++, hashtags.get(i).getContent());
                System.out.println("current prepared statement post sets: " + ps);
            }

            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            return 0;
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    public static Hashtag get(String content) {
        System.out.println("content in get: " + content);
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query = "SELECT * FROM hashtags WHERE content=?";

        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, content);
            System.out.println("prepared statement: " + ps);
            ResultSet rs = ps.executeQuery();
            System.out.println("result set" + rs);
            if(rs.next()) {
                return buildHashtagFromResult(rs);
            }
        } catch (SQLException ex) {
            Logger.getLogger(HashtagRepo.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        return null;
    }
    
    public static long update(Hashtag updatedHashtag) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        
        String query = "UPDATE hashtags SET count = ? WHERE id = ?";
        
        try {
            ps = connection.prepareStatement(query);
            ps.setLong(1, updatedHashtag.getCount());
            ps.setLong(2, updatedHashtag.getId());

            System.out.println("prepared statement: " + ps.toString());
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("sql exception caught!");
            System.out.println(e);
            return -1;
        } finally {
            System.out.println("in finally block");
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
//    public static long updatedCountAfterTwit(long hashtagId) {
    
    public static long delete(long hashtagId) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        String query = "DELETE FROM hashtags "
                + "WHERE id = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setLong(1, hashtagId);
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        return 0;
    }
    
    private static Hashtag buildHashtagFromResult(ResultSet rs) throws SQLException {
        return new Hashtag(
                rs.getLong("id"),
                rs.getLong("count"),
                rs.getString("content")
        );
    }
}
