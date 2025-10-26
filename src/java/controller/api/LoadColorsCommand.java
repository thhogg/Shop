/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.api;

import com.google.gson.Gson;
import dal.ColorDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import model.entity.Color;

/**
 *
 * @author Leo
 */
public class LoadColorsCommand implements ICommand{

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        ColorDAO colorDao = ColorDAO.getInstance();
        List<Color> colors = colorDao.getAllColors();
        Gson gson = new Gson();
        String colorsGson = gson.toJson(colors);
        
        response.setContentType("application/json"); 
        response.setCharacterEncoding("UTF-8");
        try {
            response.getWriter().write(colorsGson);
        } catch (IOException ex) {
        }
      
        return null;
    }
    
}
