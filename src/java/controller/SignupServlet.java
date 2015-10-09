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
import dataaccess.UserDB;

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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("inside of do post in signup servlet");
        String url ="";
        User user = new User();
        // get parameters from the request
        long insertResultCode = 2; // 1 means error from user db, 2 means never run
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String nickname = request.getParameter("nickname");
        String password = request.getParameter("password");
        String month = request.getParameter("month");
        String day = request.getParameter("day");
        String year = request.getParameter("year");
        Date birthdate = parseDate(month + "-" + day + "-"  + year);

        // can be removed, just a good sanity  check to make sure the
        // right values are coming in
        System.out.println(fullName);
        System.out.println("date " + birthdate);
        System.out.println("full name" + fullName + "end of full name");
        System.out.println("nickname " + nickname);
        System.out.println("email address " + email);
        System.out.println("password " + password);
        System.out.println("month " + month);
        System.out.println("day " + day);
        System.out.println("year " + year);
        
        System.out.println(!fullName.isEmpty());
        
        if(!fullName.isEmpty() && !email.isEmpty() && !nickname.isEmpty() &&
                !password.isEmpty() && birthdate != null) {
            user = new User(fullName, email, password, nickname, birthdate);
            insertResultCode = UserDB.insert(user);
        } else {
            System.out.println("insert result code: " + insertResultCode);
            url = "/signup.jsp";
            getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
        }
        System.out.println("insert result code before if statements " + insertResultCode);
        if(insertResultCode == 0) {
            request.setAttribute("user", user);
            url = "/home.jsp";
            getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
        } else {
            System.out.println("insert result code else: " + insertResultCode);
            url = "/signup.jsp";
            getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
        }
        
         
        System.out.println("\nWorking Directory = " +
              System.getProperty("user.dir") + "\n");
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
