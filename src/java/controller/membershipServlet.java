/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import business.User;
import dataaccess.UserDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author xl
 */
@WebServlet(name = "membershipServlet", urlPatterns = {"/membership"})
public class membershipServlet extends HttpServlet {
    final static String DATE_FORMAT = "M/d/yyyy";
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
        System.out.println("in do get of membership servlet");
        System.out.println(request);
        
        
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if (action == null) {
            action = "autheticate";  // default action
        } else if (action.equals("authenticate")) {
            loginPost(request, response);
        } else if(action.equals("add")) {
            signupPost(request, response);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
    public static boolean dateIsValid(String date) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
            dateFormat.setLenient(false);
            dateFormat.parse(date);
        } catch (ParseException | IllegalArgumentException e) {
            System.out.println("date is not valid " + e);
            return false;
        }
        
        return true;
    }
    
    public static boolean isUniqueEmail(String email) {
        if(UserDB.search(email) == null) {
            return true;
        };
        return false;
    }
    
    public static boolean userIsAuthenticated(String email, String password) {
        return UserDB.select(email, password) != null;
    }

    public void loginPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = "/login.jsp";
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String message;
        User user = new User();
        // validate the parameters
        if (email == null || email.isEmpty() ||
                password == null || password.isEmpty()) {
            message = "Please fill out both boxes.";
            url = "/login.jsp";
            request.setAttribute("message", message);
        } 
        else if (userIsAuthenticated(email, password)){
            message = null;
            url = "/home.jsp";

            request.setAttribute("user", user);
            request.setAttribute("message", message);
        } else {
            message = "Wrong Email / Password Combo";
            url = "/login.jsp";
            request.setAttribute("user", user);
            request.setAttribute("message", message);
        }
        getServletContext()
            .getRequestDispatcher(url)
            .forward(request, response);
    }
    
    public void signupPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String message;
        String url = "/signup";
        User user = new User();
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String nickname = request.getParameter("nickname");
        String password = request.getParameter("password");
        String month = request.getParameter("month");
        String day = request.getParameter("day");
        String year = request.getParameter("year");
        String birthdate = null;
         // 1 means error from user db, 2 means never run
        long insertResultCode = 2;

        if (dateIsValid(month + "/" + day + "/"  + year)) {
            birthdate = month + "/" + day + "/"  + year;
        }

        if(!fullName.isEmpty() && !email.isEmpty() && !nickname.isEmpty() &&
                !password.isEmpty() && birthdate != null && isUniqueEmail(email)) {
            user = new User(email, password, fullName, nickname, birthdate);
            insertResultCode = UserDB.insert(user);
        } else {
            if(!isUniqueEmail(email)) {
                message = "Please use a unique Email";
            } else {
                message = "Please fill out all fields using valid information";
            }
        }

        if(insertResultCode == 0) {
            message = "Awesome! You're all signed up!";
            request.setAttribute("user", user);
            url = "/home.jsp";
        } else {
            message = "Something went wrong, please try to sign up with valid info";
            url = "/signup.jsp";
        }
        request.setAttribute("message", message);
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
        
    }
}
