/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.io.Serializable;
//import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 *
 * @author Garrick
 */

public class Twit implements Serializable {
    private long id;
    private String email, content;
    private Date posted_date;
    
    public Twit() {}

    public Twit(String email, String content) {
        this.email = email;
        this.content = content;
        this.posted_date = new Date();
        this.id = new Random().nextInt(1000000000);
    }

    public Twit(String email, Date posted_date, String content){
        this.id = new Random().nextInt(1000000000);
        this.email = email;
        this.posted_date = posted_date;
        this.content = content;
    }

    public void setId() {
        this.id = new Random().nextInt(1000000000);
    }

    public long getId() {
        return id;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    @Override
    public String toString() {
        return "Twits{" + "email=" + email + ", content=" + content + ", posted_date=" + posted_date + '}';
    }
    
}
