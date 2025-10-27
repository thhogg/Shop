/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin;

import com.google.gson.Gson;
import dal.CategoryDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import model.dto.ProductColorData;
import model.entity.Category;

/**
 *
 * @author Leo
 */
@MultipartConfig( // Cấu hình upload
        fileSizeThreshold = 1024 * 1024 * 1, // 1MB - lưu tạm trong RAM
        maxFileSize = 1024 * 1024 * 10, // 10MB - giới hạn 1 file
        maxRequestSize = 1024 * 1024 * 50 // 50MB - giới hạn tổng request
)

public class AddProduct extends HttpServlet {

    private final Gson gson = new Gson();

    private CategoryDAO categoryDao;

    @Override
    public void init() {
        categoryDao = CategoryDAO.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Load Category    
        List<Category> categories = categoryDao.getCategoriesByParentId(0);
        request.setAttribute("categories", categories);

        request.getRequestDispatcher("add-product.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain;charset=UTF-8");

        try {
            // ==========================================================
            // A. ĐỌC DỮ LIỆU JSON
            // ==========================================================

            //Test Begin
            Collection<Part> parts = request.getParts();
            for (Part p : parts) {
                System.out.println("Part: " + p.getName() + ", file name: " + p.getSubmittedFileName());
            }
            //Test End 

            Part jsonPart = request.getPart("productColorsJson");
            if (jsonPart == null) {
                response.getWriter().write("Error: JSON data not found.");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            StringBuilder sb = new StringBuilder();
            try (InputStream is = jsonPart.getInputStream(); InputStreamReader reader = new InputStreamReader(is, StandardCharsets.UTF_8); BufferedReader br = new BufferedReader(reader)) {
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
            }
            String productColorsJson = sb.toString();

            response.getWriter().write("Received JSON: " + productColorsJson);
            response.setStatus(HttpServletResponse.SC_OK);

            java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<List<ProductColorData>>() {
            }.getType();
            List<ProductColorData> colorsData = gson.fromJson(productColorsJson, type);

            // ==========================================================
            // B. XỬ LÝ CÁC TRƯỜNG FORM VÀ FILE UPLOAD
            // ==========================================================
            // Lấy các trường form khác
            String productName = request.getParameter("productName");
            String subcategoryIdStr = request.getParameter("subcategoryId");
            String priceStr = request.getParameter("price");
            String description = request.getParameter("description");
            
            //Test Begin
            System.out.println(productName);
            System.out.println(subcategoryIdStr);
            System.out.println(priceStr);
            System.out.println(description);
            
            //Test End

            // Lưu tên file và Part tương ứng vào Map để dễ dàng ánh xạ          
            List<String> uploadedFilenames = new ArrayList<>();

            for (Part part : parts) {
                // Kiểm tra xem Part có phải là file ảnh không (bắt đầu bằng tên đã định nghĩa)
                if (part.getName().startsWith("productImageFile_") && part.getSubmittedFileName() != null) {
                    String fileName = part.getSubmittedFileName();

                    // Thư mục lưu file (trong thư mục uploads của web)
                    String uploadsDir = request.getServletContext().getInitParameter("dir-upload");
                    String uploadPath = getServletContext().getRealPath("") + uploadsDir;

                    File uploadDir = new File(uploadPath);
                    if (!uploadDir.exists()) {
                        uploadDir.mkdir();
                    }
                    part.write(uploadPath + File.separator + fileName);

                    uploadedFilenames.add(uploadsDir + fileName);
                }
            }
            // ==========================================================
            // C. KẾT HỢP DỮ LIỆU VÀ LƯU VÀO DB
            // ==========================================================
            //Test
//            System.out.println(productName);
//            System.out.println(subcategoryIdStr);
//            System.out.println(priceStr);
//            System.out.println(description);
//            System.out.println(jsonPart);
//            System.out.println("");
            // Tại bước này, bạn có:
            // 1. Dữ liệu ProductColor: List<ProductColor> colorsData (chứa tên file)
            // 2. Tên file thực tế đã upload: List<String> uploadedFilenames
            // Logic tiếp theo: 
            // - Lặp qua colorsData. 
            // - Lấy tên file từ colorsData[i].imageUrls.
            // - Kiểm tra xem file đó có nằm trong uploadedFilenames không (để xác nhận đã upload).
            // - Gọi DAO để lưu Product, ProductColor, ProductSize vào cơ sở dữ liệu.
            // Phản hồi thành công
            response.setStatus(HttpServletResponse.SC_OK);
            //response.getWriter().write("Thêm sản phẩm thành công! Đã nhận " + colorsData.size() + " biến thể màu và " + uploadedFilenames.size() + " file.");

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Server error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
