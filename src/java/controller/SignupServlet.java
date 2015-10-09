/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import business.User;
import dataaccess.UserDB;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author batesjernigan
 */
@WebServlet(name="SignupServlet", urlPatterns={"/signup"})
public class SignupServlet extends HttpServlet {
    
    final static String DATE_FORMAT = "M-d-yyyy";

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("inside of do get");
        doPost(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
        @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String url = "/signup.html";
        
        String action = request.getParameter("action");
        if (action == null) {
            action = "join";
        }
        System.out.println("\nWorking Directory = " + System.getProperty("user.dir") +"\n");
      
        if (action.equals("add")) {                
            
            // get parameters from the request
            String fullName = request.getParameter("fullName");
            String email = request.getParameter("email");
            String nickname = request.getParameter("nickname");
            String password = request.getParameter("password");
            String month = request.getParameter("month");
            String day = request.getParameter("day");
            String year = request.getParameter("year");
            Date birthdate = parseDate(month + "-" + day + "-"  + year);


            // use regular Java classes
            User user = new User(fullName, email, password, nickname, birthdate);
            UserDB.insert(user);

            // store the User object in request and set URL
            request.setAttribute("user", user);
            url = "/home.jsp";
        }
        else if (action.equals("join")) {
            // set URL to index page
            url = "/signup.html";            
        }
        getServletContext()
            .getRequestDispatcher(url)
            .forward(request, response);
    }
    @Override
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
    public static Date parseDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        dateFormat.setLenient(false);
        try {
            return dateFormat.parse(date);
        } catch (ParseException | IllegalArgumentException e) {
            return null;
        }
    }
}
