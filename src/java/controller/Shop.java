/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.CategoryDAO;
import dal.ColorDAO;
import dal.SizeDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.entity.Category;
import model.entity.Color;
import model.entity.Size;

/**
 *
 * @author Leo
 */
public class Shop extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        //Category
        CategoryDAO categoryDao = CategoryDAO.getInstance();
        List<Category> categories = categoryDao.getCategoriesByParentId(0);
        request.setAttribute("categories", categories);

        //SubCategory
        Map<Integer, List<Category>> subCategoriesMap = new HashMap<>();
        for (Category c : categories) {
            List<Category> subCategories = categoryDao.getCategoriesByParentId(c.getCategoryId());
            subCategoriesMap.put(c.getCategoryId(), subCategories);
        }
 
        request.setAttribute("subCategoriesMap", subCategoriesMap);
        
        //Size
        SizeDAO sizeDao = SizeDAO.getInstance();
        List<Size> sizes = sizeDao.getAllSizes();
        request.setAttribute("sizes", sizes);
        
        //Color
        ColorDAO colorDao = ColorDAO.getInstance();
        List<Color> colors = colorDao.getAllColors();
        request.setAttribute("colors", colors);
        
        request.getRequestDispatcher("shop.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}
