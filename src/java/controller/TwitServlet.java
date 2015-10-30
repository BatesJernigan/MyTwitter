/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import business.User;
import dataaccess.TweetDB;
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
@WebServlet(name = "twitServlet", urlPatterns = {"/twit"})
public class TwitServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String url = "/home.jsp";
        String text = request.getParameter("text");
        HttpSession session = request.getSession();
        
        if (action.equals("tweet")) {
            
            User user = (User) session.getAttribute("user");
            String email = user.getEmail();

            if(text == null || email == null){
                Tweets tweet = new Tweets(email, text);
                TweetDB.addRecord(tweet);
                url = "/home.jsp";
            }else{
                url = "/home.jsp";

            }
        }
        ArrayList<Tweets> tweets = TweetDB.all();
        session.setAttribute("tweets", tweets);
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("in do get of tweet servlet");
        
        HttpSession session = request.getSession();
        ArrayList<Tweets> tweets = TweetDB.all();
        session.setAttribute("tweets", tweets);
        //doPost(request, response);
    }
}
