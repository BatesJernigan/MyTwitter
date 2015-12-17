/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;

import business.Follow;
import business.User;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Garrick
 */
public class FollowRepo {
    
    public static long insert(Follow follow) throws IOException {
        
        ConnectionPool pool = ConnectionPool.getInstance();
        System.out.println("pool " + pool.toString());
        Connection connection = pool.getConnection();
        System.out.println("connection " + connection.toString());
        PreparedStatement ps = null;
        
        String query
                = "INSERT INTO followers (id, followed, date)"
                + "VALUES (?, ?, ?)";
        try {
            ps = connection.prepareStatement(query);
            System.out.println(follow.toString());
            ps.setLong(1, follow.getID());
            ps.setLong(2, follow.getFollowed());
            ps.setTimestamp(3, new Timestamp(follow.getDate().getTime()));

            System.out.println("ps: " + ps.toString());
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("there was a sql exception! " + e);
            System.out.println(e);
            return 0;
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    public static ArrayList<Follow> getFollwing(long id) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ArrayList<Follow> followList = new ArrayList<>();
        String query = "SELECT * FROM followers WHERE id = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Follow follow = new Follow(rs.getLong("id"), rs.getLong("followed"), rs.getDate("date"));
                followList.add(follow);
            }
            System.out.println("ps in get following follow repo: " +ps);
            return followList;
        } catch(SQLException e) {
            System.err.println(e);
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        return null;
    }
    
    public static ArrayList<Follow> all(Long id) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ArrayList<Follow> followList = new ArrayList<>();
        String query = "SELECT * FROM followers";
        try {
            ps = connection.prepareStatement(query);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Follow follow = new Follow(rs.getLong("id"), rs.getLong("followed"), rs.getDate("date"));
                followList.add(follow);
            }
            return followList;
        } catch(SQLException e) {
            System.err.println(e);
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        return null;
    }
    public static ArrayList<Follow> newfollows(Long id, Date date) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ArrayList<Follow> followList = new ArrayList<>();
        String query = "SELECT * FROM followers WHERE followed = ? AND date >= ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setLong(1, id);
            ps.setTimestamp(2, new Timestamp(date.getTime()));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Follow follow = new Follow(rs.getLong("id"), rs.getLong("followed"), rs.getDate("date"));
                followList.add(follow);
            }
            System.out.println("ps in get newfollowing follow repo: " +ps);
            return followList;
        } catch(SQLException e) {
            System.err.println(e);
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        return null;
    }
    public static int delete(String user, String followed){

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        String query = "DELETE FROM followers "
                + "WHERE id = ? AND followed = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, user);
            ps.setString(2, followed);
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        return 0;
    }
       
}
