/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import business.User;
import dataaccess.TwitRepo;
import business.Twit;
import dataaccess.UserRepo;
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
        System.out.println("session object: " + session.toString());
        
        String email = user.getEmail();
        
        System.out.println("twit content: " + content);
        System.out.println("email content: " + email);
        
        if (action.equals("twit")) {
            System.out.println("action equals twit");

            System.out.println("twit content: " + content);

            if(content != null && email != null){
                System.out.println("text != null and content != null");
                
                Twit twit = new Twit(user.getId(), content);
                System.out.println("Twit to add: " + twit);
                TwitRepo.addRecord(twit);
            }
        }
        
        ArrayList<Twit> twits = TwitRepo.all();
        for(Twit twit : twits) {
            System.out.println("twits from all call: " + twit);
        }
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
