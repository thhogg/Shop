/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.home;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import model.entity.CartItem;

/**
 *
 * @author Leo
 */
public class ShopCart extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        List<CartItem> cartItems;
        double total = 0;

        if (userId == null) {
            // User chưa đăng nhập => lấy giỏ hàng từ session guestCart
            cartItems = (List<CartItem>) session.getAttribute("guestCart");
            if (cartItems == null) {
                cartItems = new ArrayList<>();
            }
            total = cartItems.stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();

            // Đánh dấu chưa login
            request.setAttribute("loginRequired", true);

        } else {
            cartItems = new ArrayList<>();
            
            
//            // User đã đăng nhập => lấy giỏ hàng từ DB
//            cartItems = cartService.getCartItemsByUserId(userId);
//            total = cartItems.stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
//
//            // Nếu có guestCart trong session, có thể merge vào DB
//            List<CartItem> guestCart = (List<CartItem>) session.getAttribute("guestCart");
//            if (guestCart != null && !guestCart.isEmpty()) {
//                for (CartItem guestItem : guestCart) {
//                    cartService.addOrUpdateCartItem(userId, guestItem.getProductVariantID(), guestItem.getQuantity());
//                }
//                session.removeAttribute("guestCart"); // Xoá cart tạm
//                // Lấy lại từ DB sau khi merge
//                cartItems = cartService.getCartItemsByUserId(userId);
//                total = cartItems.stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
//            }
        }

        request.setAttribute("cartItems", cartItems);
        request.setAttribute("cartTotal", String.format("%.2f", total));

        request.getRequestDispatcher("shop-cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        
        //Get Parameters From product-details.jsp
        String cartItem_productVariantIDStr = request.getParameter("cartItem_productVariantID");
        String cartItem_quantityStr = request.getParameter("cartItem_quantity");
        String cartItem_productName = request.getParameter("cartItem_productName");
        String cartItem_priceStr = request.getParameter("cartItem_price");
        String cartItem_image = request.getParameter("cartItem_image");
        
        Integer cartItem_productVariantID = null;
        Integer cartItem_quantity = null;
        Integer cartItem_price = null;
        
        try {
            cartItem_productVariantID = Integer.valueOf(cartItem_productVariantIDStr);
            cartItem_quantity = Integer.valueOf(cartItem_quantityStr);
            cartItem_price = Integer.valueOf(cartItem_priceStr);
        } catch (NumberFormatException e) {
        }
        
        CartItem cartItem = new CartItem();
        cartItem.setProductVariantID(cartItem_productVariantID);
        cartItem.setProductName(cartItem_productName);
        cartItem.setImage(cartItem_image);
        cartItem.setPrice(cartItem_price);
        cartItem.setQuantity(cartItem_quantity);
               
        List<CartItem> cartItems;
        int total = 0;

        if (userId == null) {
            // User chưa đăng nhập => lấy giỏ hàng từ session guestCart
            cartItems = (List<CartItem>) session.getAttribute("guestCart");
            if (cartItems == null) {
                cartItems = new ArrayList<>();
            }
            cartItems.add(cartItem);
            total = cartItems.stream().mapToInt(item -> item.getPrice() * item.getQuantity()).sum();

            
            session.setAttribute("guestCart", cartItems);
            // Đánh dấu chưa login
            request.setAttribute("loginRequired", true);

        } else {
            cartItems = new ArrayList<>();
            
            
            
//            // User đã đăng nhập => lấy giỏ hàng từ DB
//            cartItems = cartService.getCartItemsByUserId(userId);
//            total = cartItems.stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
//
//            // Nếu có guestCart trong session, có thể merge vào DB
//            List<CartItem> guestCart = (List<CartItem>) session.getAttribute("guestCart");
//            if (guestCart != null && !guestCart.isEmpty()) {
//                for (CartItem guestItem : guestCart) {
//                    cartService.addOrUpdateCartItem(userId, guestItem.getProductVariantID(), guestItem.getQuantity());
//                }
//                session.removeAttribute("guestCart"); // Xoá cart tạm
//                // Lấy lại từ DB sau khi merge
//                cartItems = cartService.getCartItemsByUserId(userId);
//                total = cartItems.stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
//            }
        }

        session.setAttribute("cartItems", cartItems);
        request.setAttribute("cartTotal", total);

        request.getRequestDispatcher("shop-cart.jsp").forward(request, response);

    }

}
