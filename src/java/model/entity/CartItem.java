/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.entity;

/**
 *
 * @author Leo
 */
public class CartItem {
    private int cartItemID;
    private int cartID;
    private int ProductVariantID;
    private int quantity;
    
    private String productName;
    private int price;
    private String image;

    public CartItem() {
    }

    public CartItem(int ProductVariantID, int quantity, String productName, int price, String image) {
        this.ProductVariantID = ProductVariantID;
        this.quantity = quantity;
        this.productName = productName;
        this.price = price;
        this.image = image;
    }   

    public CartItem(int cartItemID, int cartID, int ProductVariantID, int quantity, String productName, int price, String image) {
        this.cartItemID = cartItemID;
        this.cartID = cartID;
        this.ProductVariantID = ProductVariantID;
        this.quantity = quantity;
        this.productName = productName;
        this.price = price;
        this.image = image;
    }

    public int getCartItemID() {
        return cartItemID;
    }

    public void setCartItemID(int cartItemID) {
        this.cartItemID = cartItemID;
    }

    public int getCartID() {
        return cartID;
    }

    public void setCartID(int cartID) {
        this.cartID = cartID;
    }

    public int getProductVariantID() {
        return ProductVariantID;
    }

    public void setProductVariantID(int ProductVariantID) {
        this.ProductVariantID = ProductVariantID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    
}
