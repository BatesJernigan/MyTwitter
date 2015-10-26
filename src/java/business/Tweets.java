/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.io.Serializable;

/**
 *
 * @author Garrick
 */

public class Tweets implements Serializable {
    private String email, date, text;
    
    public Tweets() {
        this.email = "";
        this.date ="";
        this.text ="";
    }

    public Tweets(String email, String date, String text) {
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
