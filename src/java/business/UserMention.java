/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.io.Serializable;
import java.util.Random;

/**
 *
 * @author batesjernigan
 */
public class UserMention implements Serializable{
    private long userId, twitId, id;

    public UserMention(long userId, long twitId) {
        this.userId = userId;
        this.twitId = twitId;
        this.id = new Random().nextInt(1000000000);;
    }
    
    public UserMention(long userId, long twitId, long id) {
        this.userId = userId;
        this.twitId = twitId;
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getTwitId() {
        return twitId;
    }

    public void setTwitId(long twitId) {
        this.twitId = twitId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    
}
