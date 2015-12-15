/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;
import business.User;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author xl
 */
public class UserRepo {
    
    public static long insert(User user) throws IOException {
        
        ConnectionPool pool = ConnectionPool.getInstance();
        System.out.println("pool " + pool.toString());
        Connection connection = pool.getConnection();
        System.out.println("connection " + connection.toString());
        PreparedStatement ps = null;
        
        System.out.println("user in insert method: " + user.toString());

        String query
                = "INSERT INTO users (email, password, full_name, nickname, id, birthdate, profile_picture)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            ps = connection.prepareStatement(query);
            System.out.println(user.toString());
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getFullName());
            ps.setString(4, user.getNickname());
            ps.setLong(5, user.getId());
            ps.setTimestamp(6, new Timestamp (user.getBirthdate().getTime()));
            ps.setString(7, user.getProfilePicture());

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
    
    public static long selectNickname(String nickname) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query = "SELECT id "
                + "FROM users "
                + "WHERE nickname = ?";
        try {
            User user = new User();
            ps = connection.prepareStatement(query);
            ps.setString(1, nickname);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getLong("id");
            }
        } catch(SQLException e) {
            System.err.println(e);
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        return 0;
    }
    
    public static User authenticate(String email, String password) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query = "SELECT * "
                + "FROM users "
                + "WHERE email = ? AND password = ?";
        try {
            User user = new User();
            ps = connection.prepareStatement(query);
            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return buildUserFromResult(rs);
            }
        } catch(SQLException e) {
            System.err.println(e);
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        return null;
    }
    
    // return null; if exist make a User object and return it.
    public static User search(String email) {
        ResultSet rs;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        
        String query = "SELECT * FROM users WHERE email = ?";

        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, email);

            System.out.println("prepared statement: " + ps.toString());
            rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("rs has next ");
                return buildUserFromResult(rs);
            }
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        System.out.println("returning null");
        return null;
    }
    
    public static long update(User updatedUser) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        
        String query = "UPDATE users SET "
                + "full_name = ?, "
                + "password = ?, "
                + "nickname = ?, "
                + "birthdate = ? "
                + "WHERE email = ?";
        
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, updatedUser.getFullName());
            ps.setString(2, updatedUser.getPassword());
            ps.setString(3, updatedUser.getNickname());
            ps.setDate(4, new java.sql.Date(updatedUser.getBirthdate().getTime()));
            ps.setString(5, updatedUser.getEmail());

            System.out.println("prepared statement: " + ps.toString());
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("sql exception caught!");
            System.out.println(e);
            return 2;
        } finally {
            System.out.println("in finally block");
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    public static ArrayList<User> all() {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ArrayList<User> userList = new ArrayList<>();

        String query = "SELECT * FROM users";
        try {
            ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                userList.add(buildUserFromResult(rs));
            }
            return userList;
        } catch(SQLException e) {
            System.err.println(e);
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        return null;
    }
    
    public static ArrayList<User> getWhoToFollow(User user) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ArrayList<User> userList = new ArrayList<>();
        String query = "SELECT * FROM users WHERE id != ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setLong(1, user.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                userList.add(buildUserFromResult(rs));
            }
            System.out.println("we made it here");
            return userList;
        } catch(SQLException e) {
            System.err.println(e);
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        return null;
    }
    
    private static User buildUserFromResult(ResultSet rs) throws SQLException {
        return new User(rs.getLong("id"),
            rs.getString("full_name"), 
            rs.getString("email"), 
            rs.getString("password"),
            rs.getString("nickname"),
            rs.getDate("birthdate"),
            rs.getString("profile_picture")
        );
    }
}
