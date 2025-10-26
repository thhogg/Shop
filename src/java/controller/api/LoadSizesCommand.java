/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.api;

import com.google.gson.Gson;
import dal.SizeDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import model.entity.Size;

/**
 *
 * @author Leo
 */
public class LoadSizesCommand implements ICommand{

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        SizeDAO sizeDao = SizeDAO.getInstance();
        List<Size> sizes = sizeDao.getAllSizes();
        Gson gson = new Gson();
        String sizesGson = gson.toJson(sizes);
        
        response.setContentType("application/json"); 
        response.setCharacterEncoding("UTF-8");
        try {
            response.getWriter().write(sizesGson);
        } catch (IOException ex) {
        }
      
        return null;
                
    }
    
    
}
