/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.util.ArrayList;
import java.util.List;
import model.entity.ProductImage;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.dto.ProductImageData;

/**
 *
 * @author Leo
 */
public class ProductImageDAO extends DBContext {

    private static ProductImageDAO instance;

    public ProductImageDAO() {
    }

    public static synchronized ProductImageDAO getInstance() {
        if (instance == null) {
            instance = new ProductImageDAO();
        }
        return instance;
    }

    public List<ProductImage> getByProductColorId(int productColorId) {
        List<ProductImage> list = new ArrayList<>();
        String sql = "SELECT [ImageID]\n"
                + "      ,[ProductColorID]\n"
                + "      ,[ImageUrl]\n"
                + "      ,[Main]\n"
                + "FROM [dbo].[ProductImage]\n"
                + "WHERE ProductColorID = ?";
        
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, productColorId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ProductImage pi = new ProductImage();
                pi.setImageID(rs.getInt("ImageID"));
                pi.setProductColorID(rs.getInt("ProductColorID"));
                pi.setImageUrl(rs.getString("ImageUrl"));
                pi.setMain(rs.getBoolean("Main"));
                
                list.add(pi);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return list;
    }
    
    public void insertProductImage(int productColorId, String imageUrl, boolean main) {
        
        String sql = """
                     INSERT INTO [dbo].[ProductImage]
                                ([ProductColorID]
                                ,[ImageUrl]
                                ,[Main])
                     VALUES
                     (?,?,?)""";
        
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, productColorId);
            ps.setString(2, imageUrl);
            ps.setBoolean(3, main);
            ps.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println(e);
        }
        
    }
    
    
    public List<ProductImageData> getProductImageDataByProductColorId(int productColorId) {
        List<ProductImageData> list = new ArrayList<>();
        String sql = "SELECT [ImageID]\n"
                + "      ,[ProductColorID]\n"
                + "      ,[ImageUrl]\n"
                + "      ,[Main]\n"
                + "FROM [dbo].[ProductImage]\n"
                + "WHERE ProductColorID = ?";
        
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, productColorId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ProductImageData pi = new ProductImageData();
 
                pi.setUrl(rs.getString("ImageUrl"));
                pi.setMain(rs.getBoolean("Main")==true?1:0);
                
                list.add(pi);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return list;
    }

}
