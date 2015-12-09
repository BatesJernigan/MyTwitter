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
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

/**
 *
 * @author Garrick
 */
@WebServlet(name = "TwitServlet", urlPatterns = {"/twit"})
public class TwitServlet extends HttpServlet {
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
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
        
        ArrayList<TwitView> twits = TwitViewRepo.all(user);

        session.setAttribute("twits", twits);
        
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
