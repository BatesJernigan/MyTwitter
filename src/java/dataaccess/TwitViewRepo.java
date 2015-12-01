/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;

import business.TwitView;
import business.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author batesjernigan
 */
public class TwitViewRepo {

    public static ArrayList<TwitView> all(User currentUser) {
        System.out.println("In twit view repo all");
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
}
