/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.entity;

/**
 *
 * @author Leo
 */
public class ProductVariant {
    private int productVariantID;
    private int productColorID;
    private int sizeID;
    private int quantity;
    
    private Size size;

    public ProductVariant() {
    }

    public ProductVariant(int productVariantID, int productColorID, int sizeID, int quantity, Size size) {
        this.productVariantID = productVariantID;
        this.productColorID = productColorID;
        this.sizeID = sizeID;
        this.quantity = quantity;
        this.size = size;
    }

    public int getProductVariantID() {
        return productVariantID;
    }

    public void setProductVariantID(int productVariantID) {
        this.productVariantID = productVariantID;
    }

    public int getProductColorID() {
        return productColorID;
    }

    public void setProductColorID(int productColorID) {
        this.productColorID = productColorID;
    }

    public int getSizeID() {
        return sizeID;
    }

    public void setSizeID(int sizeID) {
        this.sizeID = sizeID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }
    
    
}
