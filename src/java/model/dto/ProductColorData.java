/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.dto;

import java.util.List;

/**
 *
 * @author Leo
 */
public class ProductColorData {
    private int colorId;
    private List<ProductImageData> imageUrls;
    private List<ProductSizeData> sizes;

    public ProductColorData() {}

    public ProductColorData(int colorId, List<ProductImageData> imageUrls, List<ProductSizeData> sizes) {
        this.colorId = colorId;
        this.imageUrls = imageUrls;
        this.sizes = sizes;
    }
    
    public int getColorId() {
        return colorId;
    }

    public void setColorId(int colorId) {
        this.colorId = colorId;
    }

    public List<ProductImageData> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<ProductImageData> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public List<ProductSizeData> getSizes() {
        return sizes;
    }

    public void setSizes(List<ProductSizeData> sizes) {
        this.sizes = sizes;
    }
}