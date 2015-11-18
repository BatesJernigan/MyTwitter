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
        
//        for(Cookie cookie: cookies) {
//            if(cookie.getName().equals("emailCookie")) {
//                System.out.println("email cookie, value: " + cookie.getValue());
//                getServletContext()
//                    .getRequestDispatcher("/home.jsp")
//                    .forward(request, response);
//                return;
//            }
//        }

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
        System.out.println("in login post");
        String url = "/login.jsp";
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String message = null;
        User user = new User();
        HttpSession session = request.getSession();
        // validate the parameters
        if (email == null || email.isEmpty() ||
                password == null || password.isEmpty()) {
            message = "Please fill out both boxes.";
            url = "/login.jsp";
            request.setAttribute("message", message);
        } 
        else if (userIsAuthenticated(email, password)){
            System.out.println("user is authenticated");
            Cookie cookie = new Cookie("emailCookie", email);
            cookie.setMaxAge(60*60*24);
            cookie.setPath("/");
            response.addCookie(cookie);
            url = "/home.jsp";
            user = UserRepo.search(email);
            System.out.println("user attribute in login: " + user.toString());
            session.setAttribute("user", user);
            
            ArrayList<Twit> twitList = TwitRepo.all();
            
            for(Twit twit: twitList) {
                System.out.println("twit list in membership serv: " + twit.toString());
            }
            
            session.setAttribute("twits", twitList);
            
            request.setAttribute("message", message);
        } else {
            System.out.println("not authenticated");
            message = "Wrong Email / Password Combo";
            url = "/login.jsp";
            request.setAttribute("message", message);
        }
        getServletContext()
            .getRequestDispatcher(url)
            .forward(request, response);
    }
    
    public void signupPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String message = "";
        String url = "";
        HttpSession session = request.getSession();
        User user = new User();
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String nickname = request.getParameter("nickname");
        String password = request.getParameter("password");
        String month = request.getParameter("month");
        String day = request.getParameter("day");
        String year = request.getParameter("year");
        Date birthdate = validatedDate(month + "/" + day + "/"  + year);
        System.out.println("fullname: " + fullName); 
        System.out.println("email: " + email); 
        System.out.println("nickname: " + nickname); 
        System.out.println("password: " + password); 
        System.out.println("birthdate: " + birthdate); 

        long insertResultCode = 0;

        if(!fullName.isEmpty() && !email.isEmpty() && !nickname.isEmpty() &&
                !password.isEmpty() && birthdate != null && isUniqueEmail(email)) {
//            public User(String fullName, String email, String password, String nickname, Date birthdate) {
            user = new User(fullName, email, password, nickname, birthdate);
            insertResultCode = UserRepo.insert(user);
            System.out.println("Insert result code");
            System.out.println(insertResultCode);
            if(insertResultCode == 1) {
                message = "Awesome! You're all signed up!";
                System.out.println("user attribute in signup: " + user.toString());
                session.setAttribute("user", user);
                url = "/home.jsp";
            } else {
                message = "Something went wrong, please try to sign up with valid info";
                url = "/signup.jsp";
            }
        } else {
            if(!isUniqueEmail(email)) {
                message = "Please use a unique Email";
            }
        }
        ArrayList<User> userList = UserRepo.selectAll();
        userList.stream().forEach((currentUser) -> {
            System.out.println(currentUser.toString());
        });

        request.setAttribute("message", message);
        
        System.out.println("Sending to: " + url);
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }
}
