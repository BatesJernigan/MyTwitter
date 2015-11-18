/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.io.Serializable;
import java.util.Date;
import java.util.Random;

/**
 *
 * @author Garrick
 */

public class Twit implements Serializable {
    private long id, user_id;
    private String content;
    private Date posted_date;
    
    public Twit() {}

    public Twit(long user_id, String content) {
        this.user_id = user_id;
        this.content = content;
        this.posted_date = new Date();
        this.id = new Random().nextInt(1000000000);
    }

    public Twit(long user_id, Date posted_date, String content){
        this.id = new Random().nextInt(1000000000);
        this.user_id = user_id;
        this.posted_date = posted_date;
        this.content = content;
    }
    
    public Twit(long id, long user_id, Date posted_date, String content){
        this.id = id;
        this.user_id = user_id;
        this.posted_date = posted_date;
        this.content = content;
    }

    public void setId() {
        this.id = new Random().nextInt(1000000000);
    }

    public long getId() {
        return id;
    }

    public long getUserId() {
        return user_id;
    }

    public void setUserId(long user_id) {
        this.user_id = user_id;
    }
    
    
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPostedDate() {
        return posted_date;
    }

    public void setPostedDate(Date posted_date) {
        this.posted_date = posted_date;
    }
    
}
