/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.dto;

import java.util.Objects;


/**
 *
 * @author Leo
 */
public class ProductCard {
    private int productID;
    private String productName;
    private double price;
    private String mainImage;   
    private int categoryId;
    private int productColorId;

    public ProductCard() {
    }

    public ProductCard(int productID, String productName, double price, String mainImage, int categoryId, int productColorId) {
        this.productID = productID;
        this.productName = productName;
        this.price = price;
        this.mainImage = mainImage;
        this.categoryId = categoryId;
        this.productColorId = productColorId;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getProductColorId() {
        return productColorId;
    }

    public void setProductColorId(int productColorId) {
        this.productColorId = productColorId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.productID;
        hash = 37 * hash + Objects.hashCode(this.productName);
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.price) ^ (Double.doubleToLongBits(this.price) >>> 32));
        hash = 37 * hash + Objects.hashCode(this.mainImage);
        hash = 37 * hash + this.categoryId;
        hash = 37 * hash + this.productColorId;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ProductCard other = (ProductCard) obj;
        if (this.productID != other.productID) {
            return false;
        }
        if (Double.doubleToLongBits(this.price) != Double.doubleToLongBits(other.price)) {
            return false;
        }
        if (this.categoryId != other.categoryId) {
            return false;
        }
        if (this.productColorId != other.productColorId) {
            return false;
        }
        if (!Objects.equals(this.productName, other.productName)) {
            return false;
        }
        return Objects.equals(this.mainImage, other.mainImage);
    }
    
    
}
