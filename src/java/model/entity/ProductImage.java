/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.entity;

/**
 *
 * @author Leo
 */
public class ProductImage {
    private int imageID;
    private int productColorID;
    private String imageUrl;
    private boolean main;

    public ProductImage() {
    }

    public ProductImage(int imageID, int productColorID, String imageUrl, boolean main) {
        this.imageID = imageID;
        this.productColorID = productColorID;
        this.imageUrl = imageUrl;
        this.main = main;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public int getProductColorID() {
        return productColorID;
    }

    public void setProductColorID(int productColorID) {
        this.productColorID = productColorID;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isMain() {
        return main;
    }

    public void setMain(boolean main) {
        this.main = main;
    }

   
}
