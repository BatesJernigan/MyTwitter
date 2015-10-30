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
        
        if(action.equals("tweet")) {
            String url = "/home.jsp";
            String text = request.getParameter("text");

            System.out.println("url from request" + url);
            System.out.println("text from request" + text);
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            String email = user.getEmail();

            System.out.println("email: "+ email);

            if(text != null && email != null){
                Tweets tweet = new Tweets(email, text);

                System.out.println("tweet: "+ tweet.toString());
                TweetDB.addRecord(tweet);



                url = "/home.jsp";
            } else {
                url = "/home.jsp";    
            }
            ArrayList<Tweets> tweetList = TweetDB.all();

            session.setAttribute("tweets", tweetList);

            getServletContext()
                    .getRequestDispatcher(url)
                    .forward(request, response);
        }
    }
}
