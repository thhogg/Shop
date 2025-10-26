/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.api;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Leo
 */
public class FrontController extends HttpServlet {
    
    
    
    // Map để lưu trữ các lớp xử lý (ICommand/Action)
    private Map<String, ICommand> commands;

    @Override
    public void init() throws ServletException {
        // Ánh xạ URL path với các lớp xử lý cụ thể
        
        commands = new HashMap<>();
       
        // /loadSubCategories.do sẽ gọi LoadSubCategoriesCommand (để trả về JSON)
        commands.put("/api/loadSubCategories", new LoadSubCategoriesCommand());
        
        commands.put("/api/loadColors", new LoadColorsCommand());
        
        commands.put("/api/loadSizes", new LoadSizesCommand());
        
    }
    
    private void processRequest(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. Lấy URI và xác định ICommand cần chạy
        String uri = request.getRequestURI();
        // Cắt bỏ context path và phần mở rộng (.do) để lấy key
        String commandKey = uri.substring(request.getContextPath().length());
        commandKey = commandKey.substring(0, commandKey.lastIndexOf(".do"));
        
        // 2. Lấy đối tượng ICommand tương ứng
        ICommand command = commands.get(commandKey);
        
        if (command != null) {
            // 3. Thực thi ICommand, nhận về một chuỗi kết quả (View Path hoặc null)
            String viewPath = command.execute(request, response);

            // 4. Xử lý kết quả trả về
            if (viewPath != null) {
                // Nếu viewPath có, forward đến JSP (dành cho View Controller)
                request.getRequestDispatcher(viewPath).forward(request, response);
            } 
            // KHÔNG CÓ ELSE: Nếu viewPath là null (hoặc empty), 
            // tức là ICommand đã tự xử lý Response (ví dụ: trả về JSON) 
            // và không cần Forward.
        } else {
            // Xử lý lỗi 404
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
   

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

   
}
