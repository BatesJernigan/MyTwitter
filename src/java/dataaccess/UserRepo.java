/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;
import business.Follow;
import business.User;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

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
                = "INSERT INTO users (email, password, full_name, nickname, id, birthdate, profile_picture, password_salt)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
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
            ps.setString(8, user.getPasswordSalt());

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
        User dbUser = search(email);
        System.out.println("db user: " + dbUser);
        
        try {
            String dbHashedPassword = dbUser.getPassword();
            String passwordSalt = dbUser.getPasswordSalt();
            System.out.println("password salt: " + passwordSalt);
            String inputHashedPassword = PasswordUtil.hashPassword(passwordSalt + password);
            System.out.println("db hashed password: " + dbHashedPassword);
            System.out.println("input hashed password: " + inputHashedPassword);
            if(dbHashedPassword.equals(inputHashedPassword)) {
                return dbUser;
            }
        } catch(NoSuchAlgorithmException e) {
            System.err.println(e);
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        return null;
    }
    
//    public static String getPasswordSalt(String email) {
//        ConnectionPool pool = ConnectionPool.getInstance();
//        Connection connection = pool.getConnection();
//        PreparedStatement ps = null;
//
//        String query = "SELECT * "
//                + "FROM users "
//                + "WHERE email = ?";
//        try {
//            ps = connection.prepareStatement(query);
//            ps.setString(1, email);
//            ps.setString(2, password);
//
//            ResultSet rs = ps.executeQuery();
//            
//            if (rs.next()) {
//                return buildUserFromResult(rs);
//            }
//        } catch(SQLException e) {
//            System.err.println(e);
//        } finally {
//            DBUtil.closePreparedStatement(ps);
//            pool.freeConnection(connection);
//        }
//        return null;
//    }
    
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
                + "birthdate = ?, "
                + "lastlogin = ? "
                + "WHERE email = ?";
        
        try {
            System.out.println("update user is being called");
            ps = connection.prepareStatement(query);
            ps.setString(1, updatedUser.getFullName());
            ps.setString(2, updatedUser.getPassword());
            ps.setString(3, updatedUser.getNickname());
            ps.setDate(4, new java.sql.Date(updatedUser.getBirthdate().getTime()));
            Date date = new Date();
            ps.setDate(5, new java.sql.Date(date.getTime()));
            ps.setString(6, updatedUser.getEmail());
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
    
    // display users you are not following
    public static ArrayList<User> getWhoToFollow(User user) {
        String query = "SELECT * FROM users WHERE id != ? ";
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ArrayList<User> userList = new ArrayList<>();
        ArrayList<Follow> notFollowingList = FollowRepo.getFollwing(user.getId());
        if(!notFollowingList.isEmpty()) {
            query = query + buildQueryString(query, "id != ?", " AND ", notFollowingList.size());
        }
        System.out.println("not following list size: " + notFollowingList.size());
        try {
            ps = connection.prepareStatement(query);
            ps.setLong(1, user.getId());

            int indexForInsert = 2;
            for(int i =0; i<notFollowingList.size(); i++) {
                ps.setLong(indexForInsert++, notFollowingList.get(i).getFollowed());
            }

            System.out.println("ps in get who to follow: " +ps);

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
    
    
    // display users you are following
    public static ArrayList<User> getWhoNotToFollow(User user) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ArrayList<User> userList = new ArrayList<>();
        
        // gets list of people user is following
        ArrayList<Follow> followList = FollowRepo.getFollwing(user.getId());
        String query = "SELECT * FROM users WHERE id = ? ";
        if(!followList.isEmpty()) {
            query +=  buildQueryString(query, "id = ?", " OR ", followList.size() - 1);
        
        System.out.println("follow list size: " +followList.size());
        try {
            ps = connection.prepareStatement(query);
            //ps.setLong(1, user.getId());
            int indexForInsert = 1;
            for(int i =0; i<followList.size(); i++) {
                ps.setLong(indexForInsert++, followList.get(i).getFollowed());
            }

            System.out.println("ps in get who not to follow: " +ps);

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
            rs.getDate("lastlogin"),
            rs.getString("profile_picture"),
            rs.getString("password_salt"));
    }
    
    public static String buildQueryString(String intialQuery, String stringToAppend, String connector, int dataSize) {
        String query = "";
        for(int i=0; i<dataSize; i++) {
            query += connector;
            query = query + stringToAppend;
        }
        return query;
    }
}
