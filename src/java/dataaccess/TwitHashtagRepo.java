/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;

import business.Hashtag;
import business.Twit;
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
public class TwitHashtagRepo {
    public static ArrayList<Twit> getAllTwitByHashtagContent(String hashtagContent) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ArrayList<Twit> twitList = new ArrayList<>();

        String query = "SELECT t.* "
                + "FROM twits t "
                + "INNER JOIN twit_hashtags th "
                + "ON th.twit_id = t.id "
                + "INNER JOIN hashtags h "
                + "ON th.hashtag_id = h.id "
                + "WHERE h.content LIKE ?";
        try {
            ps = connection.prepareStatement(query);
            System.out.println("query in get all twit by hashtag id: " +query);
            ps.setString(1, "%" + hashtagContent + "%");
            System.out.println("ps in get all twit by hashtag id: " + ps);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                twitList.add(TwitRepo.buildTwitFromResult(rs));
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
    
    public static ArrayList<Hashtag> getHashtagsByTwitId(long twitId) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ArrayList<Hashtag> hashtags = new ArrayList<>();
        
        String query = "SELECT h.* FROM hashtags h " +
                "INNER JOIN twit_hashtags th " +
                "ON th.hashtag_id = h.id " +
                " INNER JOIN twits t " + 
                " ON th.twit_id = t.id " +
                " WHERE t.id = ? ";

        try {
            ps = connection.prepareStatement(query);
            ps.setLong(1, twitId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                hashtags.add(HashtagRepo.buildHashtagFromResult(rs));
            }
            return hashtags;
        } catch(SQLException e) {
            System.err.println(e);
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        return null;
    }
    
    public static ArrayList<Twit> getTrending() {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ArrayList<Hashtag> hashtags = HashtagRepo.getTrending();
        
        ArrayList<Twit> twitList = new ArrayList<>();

        String query = "SELECT t.* "
                + "FROM twits t "
                + "INNER JOIN twit_hashtags th "
                + "ON th.twit_id = t.id "
                + "INNER JOIN hashtags h "
                + "ON th.hashtag_id = h.id "
                + "WHERE ";
        try {
            String finalQuery = buildQueryString(query, "h.id = ?", " OR ", hashtags.size());
            ps = connection.prepareStatement(finalQuery);
            System.out.println("final query in get all twit by hashtag id: " +finalQuery);
            int indexForInsert = 1;
            for(int i =0; i<hashtags.size(); i++) {
                System.out.println("current prepared statement pre sets: " + ps);
                ps.setLong(indexForInsert++, hashtags.get(i).getId());
                System.out.println("current prepared statement post sets: " + ps);
            }
            System.out.println("ps in get all twit by hashtag id: " + ps);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                twitList.add(TwitRepo.buildTwitFromResult(rs));
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
    
    public static long insert(Twit twit, Hashtag hashtag) {
        ConnectionPool pool = ConnectionPool.getInstance();
        System.out.println("pool " + pool.toString());
        Connection connection = pool.getConnection();
        System.out.println("connection " + connection.toString());
        PreparedStatement ps = null;

        String query
                = "INSERT INTO twit_hashtags (twit_id, hashtag_id) "
                + "VALUES (?, ?)";
        try {
            ps = connection.prepareStatement(query);
            ps.setLong(1, twit.getId());
            ps.setLong(2, hashtag.getId());
            System.out.println("prepared statement: " + ps);
            TwitRepo.addRecord(twit);
            HashtagRepo.insert(hashtag);
            return ps.executeUpdate();
        } catch (SQLException | IOException e) {
            System.out.println("there was an exception! " + e);
            Logger.getLogger(TwitHashtagRepo.class.getName()).log(Level.SEVERE, null, e);
            return 0;
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    public static long insertAll(Twit twit, ArrayList<Hashtag> hashtags) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        
        System.out.println("inside of insert all");

        String query
                = "INSERT INTO twit_hashtags (twit_id, hashtag_id) VALUES ";
        try {
            String finalQuery = buildQueryString(query, "(?, ?)", ", ", hashtags.size());
            int indexForInsert = 1;
            ps = connection.prepareStatement(finalQuery);
            for(int i =0; i<hashtags.size(); i++) {
                ps.setLong(indexForInsert++, twit.getId());
                ps.setLong(indexForInsert++, hashtags.get(i).getId());
                System.out.println("prepared statment: " + ps);
            }

            System.out.println("prepared statement in insert all: " + ps);
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("there was an exception! " + e);
            Logger.getLogger(TwitHashtagRepo.class.getName()).log(Level.SEVERE, null, e);
            return 0;
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    public static long deleteByTwitId(long twitId) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        String query = "DELETE FROM twit_hashtags "
                + "WHERE twit_id = ?";
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
    
    public static String buildQueryString(String intialQuery, String stringToAppend, String connector, int dataSize) {
        String query = intialQuery;
        for(int i=0; i<dataSize; i++) {
            query += stringToAppend;
            if(i != dataSize - 1) {
                query += connector;
            }
        }
        return query;
    }
}
