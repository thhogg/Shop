/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.entity;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Leo
 */
public class Product {
    private int productID;
    private int categoryID; 
    private String productName;
    private Double price;
    private String description;
    private Date createdAt;

    private List<ProductColor> productColors; 

    public Product() {
    }

    public Product(int productID, int categoryID, String productName, Double price, String description, List<ProductColor> productColors) {
        this.productID = productID;
        this.categoryID = categoryID;
        this.productName = productName;
        this.price = price;
        this.description = description;
        setCreatedAt();
        this.productColors = productColors;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    
    public void setCreatedAt() {
        this.createdAt = Date.valueOf(LocalDate.now());
    }

    public List<ProductColor> getProductColors() {
        return productColors;
    }

    public void setProductColors(List<ProductColor> productColors) {
        this.productColors = productColors;
    }

    @Override
    public String toString() {
        return "Product{\n" + " productID = " + productID 
                + ",\n categoryID = " + categoryID 
                + ",\n productName = " + productName 
                + ",\n price = " + price 
                + ",\n description = " + description    
                + ",\n createdAt = " + createdAt + "\n}";
    }

        
    
}
