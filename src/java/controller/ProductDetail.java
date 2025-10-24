/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.ProductDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;
import model.entity.Product;
import model.entity.ProductColor;
import model.entity.ProductImage;
import model.entity.ProductVariant;

/**
 *
 * @author Leo
 */
public class ProductDetail extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        PrintWriter out = response.getWriter();

        ProductDAO productDao = ProductDAO.getInstance();

        String productIdStr = request.getParameter("id");
        if (productIdStr == null || productIdStr.trim().isEmpty()) {
//            out.println("ProductId null");
//            return;
            response.sendRedirect("404.jsp");
            return;
        }

        int productId;
        try {
            productId = Integer.parseInt(productIdStr);
        } catch (NumberFormatException e) {
            response.sendRedirect("404.jsp");
            return;
        }

        Product product = productDao.getProductById(productId);
        if (product == null) {
            response.sendRedirect("404.jsp");
            return;
        }
        request.setAttribute("product", product);

        List<ProductColor> productColors = product.getProductColors();
        if (productColors == null || productColors.isEmpty()) {
            response.sendRedirect("404.jsp");
            return;
        }

        String productColorIdStr = request.getParameter("color_radio");
        if (productColorIdStr == null || productColorIdStr.trim().isEmpty()) {
            // Lấy màu đầu tiên làm mặc định
            productColorIdStr = String.valueOf(productColors.get(0).getProductColorID());
        }

        int productColorId;
        try {
            productColorId = Integer.parseInt(productColorIdStr);
        } catch (NumberFormatException e) {
            response.sendRedirect("404.jsp");
            return;
        }

        List<ProductImage> images = null;
        List<ProductVariant> variants = null;

        for (ProductColor pc : productColors) {
            if (pc.getProductColorID() == productColorId) {
                images = pc.getImages();
                variants = pc.getVariants();
                break;
            }
        }

        if (images == null || variants == null || variants.isEmpty()) {
            response.sendRedirect("404.jsp");
            return;
        }

        request.setAttribute("productColorIdRadio", productColorId);
        request.setAttribute("images", images);

        // Lấy size_radio từ request
        String productVariantIdStr = request.getParameter("size_radio");

        // Kiểm tra nếu productVariantIdStr null hoặc màu mới được chọn thì reset size sang size đầu tiên
        // Nếu màu mới được chọn thì luôn lấy size đầu tiên của màu đó
        // Cách nhận biết "màu mới" có thể là productVariantIdStr null hoặc không thuộc variants hiện tại
        boolean isValidSize = false;
        int productVariantId = 0;
        if (productVariantIdStr != null && !productVariantIdStr.trim().isEmpty()) {
            try {
                productVariantId = Integer.parseInt(productVariantIdStr);
                // Kiểm tra productVariantId thuộc variants hiện tại không
                for (ProductVariant pv : variants) {
                    if (pv.getProductVariantID() == productVariantId) {
                        isValidSize = true;
                        break;
                    }
                }
            } catch (NumberFormatException e) {
                // Không hợp lệ, sẽ reset size
                isValidSize = false;
            }
        }

        if (!isValidSize) {
            // Nếu không hợp lệ hoặc null thì lấy size đầu tiên của variants
            productVariantId = variants.get(0).getProductVariantID();
        }

        request.setAttribute("productVariantIdRadio", productVariantId);
        request.setAttribute("variants", variants);

        int quantity = 0;
        for (ProductVariant pv : variants) {
            if (pv.getProductVariantID() == productVariantId) {
                quantity = pv.getQuantity();
                break;
            }
        }
        request.setAttribute("availability", quantity);

        request.getRequestDispatcher("product-details.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

//        ProductDAO productDao = ProductDAO.getInstance();
//
//        String productIdStr = request.getParameter("id");
//        if (productIdStr == null) {
//            response.sendRedirect("404.jsp");
//            return;
//        }
//
//        int productId = Integer.parseInt(productIdStr);
//        Product product = productDao.getProductById(productId);
//
//        if (product == null) {
//            response.sendRedirect("404.jsp");
//            return;
//        }
//
//        request.setAttribute("product", product);
//
//        String productColorIdStr = request.getParameter("color_radio");
//
//        int productColorId = Integer.parseInt(productColorIdStr);
//        List<ProductVariant> variants = getVariantsByProductColorId(product.getProductColors(), productColorId);
//
//        request.setAttribute("variants", variants);
//
//        request.getRequestDispatcher("product-details.jsp").forward(request, response);

    }

    public List<ProductVariant> getVariantsByProductColorId(List<ProductColor> productColors, int productColorId) {
        for (ProductColor pc : productColors) {
            if (pc.getProductColorID() == productColorId) {
                return pc.getVariants();
            }
        }
        return null;
    }

    public int getQuantityByProductVariantId(List<ProductVariant> variants, int productVariantId) {
        for (ProductVariant pv : variants) {
            if (pv.getProductVariantID() == productVariantId) {
                return pv.getQuantity();
            }
        }
        return 0;
    }

}
