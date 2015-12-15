/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import business.User;
import dataaccess.TwitRepo;
import business.Twit;
import business.TwitView;
import dataaccess.TwitViewRepo;
import dataaccess.UserRepo;
import business.Follow;
import dataaccess.FollowRepo;
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
@WebServlet(name = "TwitServlet", urlPatterns = {"/twit"})
public class TwitServlet extends HttpServlet {
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        String url = "/home.jsp";
        String content = request.getParameter("content");
        
        User user = (User) session.getAttribute("user");
        String email = user.getEmail();
        
        if (action.equals("twit")) {
            if(content != null && email != null){
                Twit twit = new Twit(user.getId(), content);
                TwitRepo.addRecord(twit);
            }
        } else if(action.equals("Delete")) {
            System.out.println("action equals DELETE");
            long twitId = Long.parseLong(request.getParameter("twitId"));
            long authorId = Long.parseLong(request.getParameter("userId"));
            System.out.println("twitId: " + twitId + "end");
            if(authorId == user.getId()) {
                TwitRepo.delete(twitId, user);
            }
        }
        
        ArrayList<Follow> notFollowingList = FollowRepo.getNotFollowing(user.getId());
        ArrayList<Follow> followingList = FollowRepo.getFollwing(user.getId());
        ArrayList<TwitView> twits = TwitViewRepo.all(user, followingList);
        session.setAttribute("twits", twits);
        session.setAttribute(email, url);
        
        getServletContext()
            .getRequestDispatcher(url)
            .forward(request, response);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("in do get of twit servlet");

        HttpSession session = request.getSession();
        ArrayList<Twit> twits = TwitRepo.all();
        session.setAttribute("twits", twits);
        doPost(request, response);
    }
}
