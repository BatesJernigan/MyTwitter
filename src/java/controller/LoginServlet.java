/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import business.User;
import dataaccess.UserDB;

/**
 *
 * @author batesjernigan
*/
@WebServlet(name="LoginServlet", urlPatterns={"/login"})
public class LoginServlet extends HttpServlet {
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
        //processRequest(request, response);
        
        String url = "/login.jsp";
        
        // get current action
        String action = request.getParameter("action");
        if (action == null) {
            action = "autheticate";  // default action
        }

        if (action.equals("authenticate")) {
            System.out.println("in authenticate action");
            // get parameters from the request
            String password = request.getParameter("password");
            String email = request.getParameter("email");
            System.out.println("password " + password);
            System.out.println("email " + email);

            // store data in User object
            User user = new User();

            // validate the parameters
            String message;
            if (email == null || email.isEmpty() ||
                    password == null || password.isEmpty()) {
                message = "Please fill out both boxes.";
                url = "/login.jsp";
                request.setAttribute("message", message);
            } 
            else if (userIsAuthenticated(email, password)){
                System.out.println("user is authenticated!");
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
            
        } else if (action.equals("cancel")) {
            url = "/login.jsp";
            request.setAttribute("message", null);
        }
        getServletContext()
            .getRequestDispatcher(url)
            .forward(request, response);
    }
    
    public static boolean userIsAuthenticated(String email, String password) {
        System.out.println("in user is authenticated " + UserDB.select(email, password) != null);
        return UserDB.select(email, password) != null;
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

}
