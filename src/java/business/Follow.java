/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;
import java.util.Date;

/**
 *
 * @author Garrick
 */
public class Follow {
    private long user;
    private long followed;
    private Date date;
    public Follow(long user, long followed){
        this.user = user;
        this.followed = followed;
        this.date = new Date();
        
    }
    public Follow(long user, long followed, Date date){
        this.user = user;
        this.followed = followed;
        this.date = date;
    }
    
    public long getID(){
        return user;
    }
    public long getFollowed(){
        return followed;
    }
    public Date getDate(){
        return date;
    }
}
