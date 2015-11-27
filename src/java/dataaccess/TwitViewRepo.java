/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;

import business.Twit;
import business.TwitView;
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

    public static ArrayList<TwitView> all() {
        System.out.println("in twit all");
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ArrayList<TwitView> twitList = new ArrayList<>();

        String query = "SELECT * FROM v_twits";
        try {
            ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                twitList.add(buildTwitViewFromResult(rs));
            }
            System.out.println("twit list from all");
            
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
            rs.getString("content"),
            rs.getString("full_name"),
            rs.getString("nickname"),
            rs.getString("email"),
            rs.getDate("posted_date"));
    }
}
