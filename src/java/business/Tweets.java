/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Garrick
 */

public class Tweets implements Serializable {
    private String email, text, date;
    
    public Tweets() {
        this.email = "";
        this.date = null;
        this.text ="";
    }

    public Tweets(String email, String text) {
        this.email = email;
        this.text = text;
        
        String DATE_FORMAT_NOW = "yyyy-MM-dd";
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        this.date = sdf.format(date);

    }

    public Tweets(String email, String date, String text){
        this.email = email;
        this.date = date;
        this.text = text;
        
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    @Override
    public String toString() {
        return "User{" + "text=" + text + ", email=" + email + ", date=" + date + "}";
    }
}
