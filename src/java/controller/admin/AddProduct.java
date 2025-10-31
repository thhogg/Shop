/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dal.CategoryDAO;
import dal.ProductColorDAO;
import dal.ProductDAO;
import dal.ProductImageDAO;
import dal.ProductVariantDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import model.dto.ProductColorData;
import model.dto.ProductImageData;
import model.dto.ProductSizeData;
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
    private ProductDAO productDao;
    private ProductColorDAO productColorDao;
    private ProductImageDAO productImageDao;
    private ProductVariantDAO productVariantDao;

    @Override
    public void init() {
        categoryDao = CategoryDAO.getInstance();
        productDao = ProductDAO.getInstance();
        productColorDao = ProductColorDAO.getInstance();
        productImageDao = ProductImageDAO.getInstance();
        productVariantDao = ProductVariantDAO.getInstance();
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

        try {
            // ==========================================================
            // A. ĐỌC DỮ LIỆU JSON
            // ==========================================================

            Part jsonPart = request.getPart("productColorsJson");
            String jsonData = null;
            if (jsonPart != null) {
                try (InputStream inputStream = jsonPart.getInputStream()) {
                    jsonData = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                }
            } else {
                jsonData = request.getParameter("productColorsJson");
            }

            if (jsonData == null || jsonData.isEmpty()) {
                throw new RuntimeException("Không nhận được dữ liệu productColorsJson!");
            }

            // Parse JSON
            List<ProductColorData> productColors = gson.fromJson(
                    jsonData, new TypeToken<List<ProductColorData>>() {
                    }.getType()
            );

            // ==========================================================
            // B. XỬ LÝ CÁC TRƯỜNG FORM VÀ FILE UPLOAD
            // ==========================================================
            // Lấy các trường form khác
            String productName = request.getParameter("productName");
            String subcategoryIdStr = request.getParameter("subcategoryId");
            String priceStr = request.getParameter("price");
            String description = request.getParameter("description");
            int subCategoryId = 0;
            int price = 0;
            try {
                subCategoryId = Integer.parseInt(subcategoryIdStr);
                price = Integer.parseInt(priceStr);
            } catch (NumberFormatException e) {
            }

            // Save file 
            Collection<Part> parts = request.getParts();

            // Search forder to save file in web.xml
            String uploadsDir = request.getServletContext().getInitParameter("dir-upload");

            for (Part part : parts) {
                // Kiểm tra xem Part có phải là file ảnh không (bắt đầu bằng tên đã định nghĩa)
                if (part.getName().startsWith("productImageFile_") && part.getSubmittedFileName() != null) {
                    String fileName = part.getSubmittedFileName();

                    String uploadPath = getServletContext().getRealPath("") + uploadsDir;

                    File uploadDir = new File(uploadPath);
                    if (!uploadDir.exists()) {
                        uploadDir.mkdir();
                    }
                    part.write(uploadPath + File.separator + fileName);

                }
            }
            // ==========================================================
            // C. KẾT HỢP DỮ LIỆU VÀ LƯU VÀO DB
            // ==========================================================

            //Insert Product
            int newProductId = productDao.insertProduct(
                    subCategoryId,
                    productName,
                    price,
                    description);

            //Insert ProductColor, ProductImage, ProductVariant
            for (ProductColorData p : productColors) {
                //Insert ProductColor
                int newProductColorId = productColorDao.insertProductColor(
                        newProductId, 
                        p.getColorId());
                
                //Insert ProductImage
                for (ProductImageData pi : p.getImageUrls()) {                   
                    //uploadsDir : saved dir from web.xml
                    productImageDao.insertProductImage(
                            newProductColorId, 
                            (uploadsDir + "/" + pi.getUrl()), 
                            (pi.getMain()==1));
                }
                
                //Insert ProductVariant
                for(ProductSizeData ps : p.getSizes()) {
                    productVariantDao.insertProductVariant(
                            newProductColorId, 
                            ps.getSizeId(), 
                            ps.getQuantity());
                }
            }

            response.setContentType("text/plain;charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Upload Product Successfully");

        } catch (ServletException | IOException | RuntimeException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Server error: " + e.getMessage());
        }
    }
}
