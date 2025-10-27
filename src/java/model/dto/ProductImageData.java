/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.dto;

/**
 *
 * @author Leo
 */

public class ProductImageData {
    private String url;
    private int main;

    public ProductImageData() {}

    public ProductImageData(String url, int main) {
        this.url = url;
        this.main = main;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getMain() {
        return main;
    }

    public void setMain(int main) {
        this.main = main;
    }
}