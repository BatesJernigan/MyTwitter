/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;

import business.Twit;
import business.TwitView;
import business.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 *
 * @author batesjernigan
 */
public class TwitViewRepo {

    public static ArrayList<TwitView> all(User currentUser) {
        System.out.println("In twit view repo all currentUser: " + currentUser);
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ArrayList<TwitView> twitList = new ArrayList<>();

        String query = "SELECT * FROM v_twits "
                + "WHERE user_id = ? OR mentioned_user_id = ? "
                + "ORDER BY posted_date DESC";
            
        try {
            ps = connection.prepareStatement(query);
            ps.setLong(1, currentUser.getId());
            ps.setLong(2, currentUser.getId());
            System.out.println(ps);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TwitView currentTwit = buildTwitViewFromResult(rs);
                
                twitList.add(currentTwit);
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
    
    public static ArrayList<TwitView> getByHashtagContent(String hashtagContent) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        System.out.println("hastag content2: " + hashtagContent);
//        hastag content: <a href="/MyTwitter/twit?q=hello" style="color:blue">hello</a>
        ArrayList<Twit> twitList = TwitHashtagRepo.getAllTwitByHashtagContent(hashtagContent);
        System.out.println("twit list size in get by hashtag content: " +twitList.size());
        ArrayList<TwitView> twitViewList = new ArrayList<>();

        String query = "SELECT * FROM v_twits WHERE ";
        String finalQuery = buildQueryString(query, "twit_id = ? ", " OR ", twitList.size());
        try {
            System.out.println("final query: " + finalQuery);
            ps = connection.prepareStatement(finalQuery);
            ps.setString(1, hashtagContent);
            
            int indexForInsert = 1;
            for(int i =0; i<twitList.size(); i++) {
                ps.setLong(indexForInsert++, twitList.get(i).getId());
            }

            System.out.println("prepared statement: " + ps);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                twitViewList.add(buildTwitViewFromResult(rs));
            }
            return twitViewList;
        } catch(SQLException e) {
            System.err.println(e);
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        return null;
    }
    
    public static ArrayList<TwitView> getByTrending() {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ArrayList<Twit> twitList = TwitHashtagRepo.getTrending();
        if(twitList != null) {
            System.out.println("twit list size in get by hashtag content: " +twitList.size());
            ArrayList<TwitView> twitViewList = new ArrayList<>();

            String query = "SELECT * FROM v_twits WHERE ";
            String finalQuery = buildQueryString(query, "twit_id = ? ", " OR ", twitList.size());
            try {
                System.out.println("final query: " + finalQuery);
                ps = connection.prepareStatement(finalQuery);

                int indexForInsert = 1;
                for(int i =0; i<twitList.size(); i++) {
                    ps.setLong(indexForInsert++, twitList.get(i).getId());
                }

                System.out.println("prepared statement: " + ps);

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    twitViewList.add(buildTwitViewFromResult(rs));
                }
                return twitViewList;
            } catch(SQLException e) {
                System.err.println(e);
            } finally {
                DBUtil.closePreparedStatement(ps);
                pool.freeConnection(connection);
            }
        }
        return null;
    }

    private static TwitView buildTwitViewFromResult(ResultSet rs) throws SQLException {
        return new TwitView(rs.getLong("user_id"),
            rs.getLong("twit_id"),
            rs.getLong("mentioned_user_id"),
            rs.getString("content"),
            rs.getString("full_name"),
            rs.getString("nickname"),
            rs.getString("email"),
            rs.getDate("posted_date"));
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
