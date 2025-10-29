/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.home;

import dal.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.entity.User;

/**
 *
 * @author Leo
 */
public class Login extends HttpServlet {
    
    private UserDAO userDao;
    
    @Override
    public void init() {
        userDao =UserDAO.getInstance();
    }
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    } 

  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user = userDao.getUserByNameAndPassword(username, password);
        if (user==null) {
            String mess = "Invalid Username or Password";
            request.setAttribute("error", mess);
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } else {
            session.setAttribute("account", user);
            if (user.getRole()==0) {
                response.sendRedirect("admin/dashboard.jsp");
            } else if (user.getRole()==1) {
                response.sendRedirect("shop");
            }
        }
        
    }


}
