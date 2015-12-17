/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Garrick
 */

public class Twit implements Serializable {
    private long id, userId, mentionedUserId;
    private String content;
    private Date postedDate;
    
    public Twit() {}

    public Twit(long userId, String content) {
        this.userId = userId;
        this.content = content;
        this.postedDate = new Date();
        this.id = new Random().nextInt(1000000000);
    }

    public Twit(long userId, Date postedDate, String content){
        this.id = new Random().nextInt(1000000000);
        this.userId = userId;
        this.postedDate = postedDate;
        this.content = content;
    }

    public Twit(long id, long userId, long mentionedUserId, String content, Date postedDate) {
        this.id = id;
        this.userId = userId;
        this.mentionedUserId = mentionedUserId;
        this.content = content;
        this.postedDate = postedDate;
    }

    public void setId() {
        this.id = new Random().nextInt(1000000000);
    }

    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
    
    
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(Date postedDate) {
        this.postedDate = postedDate;
    }

    public long getMentionedUserId() {
        return mentionedUserId;
    }

    public void setMentionedUserId(long mentionedUserId) {
        this.mentionedUserId = mentionedUserId;
    }

    @Override
    public String toString() {
        return "Twit{" + "id=" + id + ", userId=" + userId + ", mentionedUser="
                + mentionedUserId + ", content=" + content + ", postedDate="
                + postedDate + '}';
    }
}
