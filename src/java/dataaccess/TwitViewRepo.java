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
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ArrayList<TwitView> twitList = new ArrayList<>();

        String query = "SELECT * FROM v_twits ORDER BY posted_date DESC;";
        try {
            ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                TwitView currentTwit = buildTwitViewFromResult(rs);
                
                String adjusted = "";
                
                for (String wordFromContent: currentTwit.getContent().split(" ")){
                    if(wordFromContent.indexOf("@") == 0 || wordFromContent.indexOf("#") == 0){
                        wordFromContent = "<span style=\"color:blue\">" + wordFromContent + "</span>";
                    }
                    adjusted += " " + wordFromContent;     
                }
                System.out.println("adjusted stuff");
                System.out.println(adjusted);
                currentTwit.setContent(adjusted);
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
            rs.getString("content"),
            rs.getString("full_name"),
            rs.getString("nickname"),
            rs.getString("email"),
            rs.getDate("posted_date"));
    }
}
