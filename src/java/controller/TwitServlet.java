/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import business.User;
import controller.TweetIO;
import business.Tweets;
import dataaccess.UserDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 *
 * @author Garrick
 */
public class TwitServlet {
    public void twitPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String url = "/home.jsp";
        String text = request.getParameter("text");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String email = user.getEmail();

        if(text == null || email == null){
            Tweets tweet = new Tweets(email, text);
            TweetIO.addRecord(tweet);
            url = "/home.jsp";
        }else{
            url = "/home.jsp";
            
        }
        
    }
}
