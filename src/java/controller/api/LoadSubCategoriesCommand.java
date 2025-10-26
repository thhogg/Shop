/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.api;

import com.google.gson.Gson;
import dal.CategoryDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.entity.Category;

/**
 *
 * @author Leo
 */

public class LoadSubCategoriesCommand implements ICommand {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        //Get Data from Database
        
        CategoryDAO categoryDao = CategoryDAO.getInstance();

        List<Category> categories = categoryDao.getCategoriesByParentId(0);
        request.setAttribute("categories", categories);

        Map<Integer, List<Category>> subCategoriesMap = new HashMap<>();
        for (Category c : categories) {
            List<Category> subCategories = categoryDao.getCategoriesByParentId(c.getCategoryId());
            subCategoriesMap.put(c.getCategoryId(), subCategories);
        }

        // 2. Chuyển Map thành chuỗi JSON và gửi về
        Gson gson = new Gson();
        String subCategoriesJson = gson.toJson(subCategoriesMap);

        response.setContentType("application/json"); // Xử lý lỗi IO
        response.setCharacterEncoding("UTF-8");
        try {
            response.getWriter().write(subCategoriesJson);
        } catch (IOException ex) {
        }

        // 3. Trả về null để báo cho Front Controller biết không cần Forward
        return null;
    }
}
