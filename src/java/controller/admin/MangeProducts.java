/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.admin;

import dal.CategoryDAO;
import dal.ProductDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.entity.Product;


/**
 *
 * @author Leo
 */
public class MangeProducts extends HttpServlet {
    
    private ProductDAO productDao;
    
    @Override
    public void init() {
        productDao = productDao.getInstance();
    }
    

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        List<Product> list = productDao.getAllProducts();
        for(Product p : list) {
            System.out.println(p);
        }
        CategoryDAO categoryDao = CategoryDAO.getInstance();
        
        request.setAttribute("categoryDao", categoryDao);
        request.setAttribute("products", list);
        request.getRequestDispatcher("manage-products.jsp").forward(request, response);
    } 

  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
       
    }

    
}
