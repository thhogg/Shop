/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.entity;

import java.util.List;

/**
 *
 * @author Leo
 */
public class ProductColor {
    private int productColorID;
    private int productID;
    private int colorID;

    private Color color; 
    private List<ProductImage> images; 
    private List<ProductVariant> variants;

    public ProductColor() {
    }

    public ProductColor(int productColorID, int productID, int colorID, Color color, List<ProductImage> images, List<ProductVariant> variants) {
        this.productColorID = productColorID;
        this.productID = productID;
        this.colorID = colorID;
        this.color = color;
        this.images = images;
        this.variants = variants;
    }

    public int getProductColorID() {
        return productColorID;
    }

    public void setProductColorID(int productColorID) {
        this.productColorID = productColorID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getColorID() {
        return colorID;
    }

    public void setColorID(int colorID) {
        this.colorID = colorID;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public List<ProductImage> getImages() {
        return images;
    }

    public void setImages(List<ProductImage> images) {
        this.images = images;
    }

    public List<ProductVariant> getVariants() {
        return variants;
    }

    public void setVariants(List<ProductVariant> variants) {
        this.variants = variants;
    }
    
    
}
