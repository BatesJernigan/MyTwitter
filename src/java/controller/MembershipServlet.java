/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import business.Twit;
import business.User;
import dataaccess.TwitRepo;
import dataaccess.UserRepo;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author xl
 */
@WebServlet(name = "membershipServlet", urlPatterns = {"/membership"})
public class MembershipServlet extends HttpServlet {
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

        //doPost(request, response);
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
        System.out.println("in do post of membership servlet");
        String action = request.getParameter("action");
        
        Cookie[] cookies = request.getCookies();
        
        for(Cookie cookie: cookies) {
            if(cookie.getName().equals("emailCookie")) {
                System.out.println("email cookie, value: " + cookie.getValue());
                getServletContext()
                    .getRequestDispatcher("/home.jsp")
                    .forward(request, response);
            }
        }
        
        
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

    public static Date validatedDate(String date) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
            dateFormat.setLenient(false);
            return dateFormat.parse(date);
        } catch (ParseException | IllegalArgumentException e) {
            System.out.println("date is not valid " + e);
            return null;
        }
    }
    
    public static boolean isUniqueEmail(String email) {
        User user;
        if((user = UserRepo.search(email)) == null) {
            System.out.println("email is unique");
            return true;
        };
        System.out.println("email is not unique");
        System.out.println(user.toString());
        return false;
    }
    
    public static boolean userIsAuthenticated(String email, String password) {
        return UserRepo.select(email, password) != null;
    }

    public void loginPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = "/login.jsp";
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String message = null;
        User user = new User();
        // validate the parameters
        if (email == null || email.isEmpty() ||
                password == null || password.isEmpty()) {
            message = "Please fill out both boxes.";
            url = "/login.jsp";
            request.setAttribute("message", message);
        } 
        else if (userIsAuthenticated(email, password)){
            Cookie cookie = new Cookie("emailCookie", email);
            cookie.setMaxAge(60*60*24);
            cookie.setPath("/");
            response.addCookie(cookie);
            url = "/home.jsp";
            user = UserRepo.search(email);
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            
            ArrayList<User> users = UserRepo.selectAll();
            
            session.setAttribute("users", users);

            for(User cUser : users) {
                System.out.println("cUser: " + cUser.toString());
            }
            
            ArrayList<Twit> twitList = TwitRepo.all();
            session.setAttribute("twits", twitList);

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
        Date birthdate = validatedDate(month + "/" + day + "/"  + year);
         // 1 means error from user db, 2 means never run
        long insertResultCode = 2;

        if(!fullName.isEmpty() && !email.isEmpty() && !nickname.isEmpty() &&
                !password.isEmpty() && birthdate != null && isUniqueEmail(email)) {
            user = new User(email, password, fullName, nickname, birthdate);
            insertResultCode = UserRepo.insert(user);
            System.out.println("Insert result code");
            System.out.println(insertResultCode);
        } else {
            if(!isUniqueEmail(email)) {
                message = "Please use a unique Email";
            } else {
                message = "Please fill out all fields using valid information";
            }
            url = "/signup.jsp";
        }
        
        ArrayList<User> userList = UserRepo.selectAll();
        for(User currentUser : userList) {
            System.out.println(currentUser.toString());
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
        
        System.out.println("Sending to: " + url);
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
        
    }
}
