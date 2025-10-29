package controller.admin;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dal.CategoryDAO;
import dal.ProductColorDAO;
import dal.ProductDAO;
import dal.ProductImageDAO;
import dal.ProductVariantDAO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.dto.ProductColorData;
import model.dto.ProductImageData;
import model.dto.ProductSizeData;
import model.entity.Category;
import model.entity.Product;

@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 1,
    maxFileSize = 1024 * 1024 * 10,
    maxRequestSize = 1024 * 1024 * 50
)
public class EditProduct extends HttpServlet {

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
        // Lấy productId từ param
        String productIdStr = request.getParameter("productId");
        if (productIdStr == null) {
            response.sendRedirect("products");
            return;
        }
        int productId = Integer.parseInt(productIdStr);

        // Load product info
        Product product = productDao.getProductById(productId);
        if (product == null) {
            response.sendRedirect("products");
            return;
        }

        // Load categories cha (parentId=0)
        List<Category> categories = categoryDao.getCategoriesByParentId(0);

        // Load productColors + sizes + images
        List<ProductColorData> productColors = productColorDao.getProductColorDatasByProductId(productId);

        // Chuyển sang JSON
        String productColorsJson = gson.toJson(productColors);

        // Truyền dữ liệu xuống JSP
        request.setAttribute("product", product);
        request.setAttribute("categories", categories);
        request.setAttribute("productColorsJson", productColorsJson);

        request.getRequestDispatcher("edit-product.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Đọc JSON productColorsJson
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

            List<ProductColorData> productColors = gson.fromJson(
                    jsonData, new TypeToken<List<ProductColorData>>() {
                    }.getType()
            );

            // Lấy các trường form khác
            String productIdStr = request.getParameter("productId");
            String productName = request.getParameter("productName");
            String subcategoryIdStr = request.getParameter("subcategoryId");
            String priceStr = request.getParameter("price");
            String description = request.getParameter("description");

            int productId = Integer.parseInt(productIdStr);
            int subCategoryId = Integer.parseInt(subcategoryIdStr);
            int price = Integer.parseInt(priceStr);

            // Lưu file
            Collection<Part> parts = request.getParts();
            String uploadsDir = request.getServletContext().getInitParameter("dir-upload");
            String uploadPath = getServletContext().getRealPath("") + uploadsDir;
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdir();

            for (Part part : parts) {
                if (part.getName().startsWith("productImageFile_") && part.getSubmittedFileName() != null) {
                    String fileName = part.getSubmittedFileName();
                    part.write(uploadPath + File.separator + fileName);
                }
            }

            // Cập nhật product
            productDao.updateProduct(productId, subCategoryId, productName, price, description);

            // Xóa dữ liệu cũ của sản phẩm (color, image, variant)
            productColorDao.deleteByProductId(productId);

            // Insert lại productColors, images, variants
            for (ProductColorData p : productColors) {
                int newProductColorId = productColorDao.insertProductColor(productId, p.getColorId());

                for (ProductImageData pi : p.getImageUrls()) {
                    productImageDao.insertProductImage(newProductColorId, (uploadsDir + "/" + pi.getUrl()), (pi.getMain() == 1));
                }

                for (ProductSizeData ps : p.getSizes()) {
                    productVariantDao.insertProductVariant(newProductColorId, ps.getSizeId(), ps.getQuantity());
                }
            }

            response.setContentType("text/plain;charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Update Product Successfully");

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Server error: " + e.getMessage());
        }
    }
}