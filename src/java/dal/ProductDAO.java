/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.entity.Product;

/**
 *
 * @author Leo
 */
public class ProductDAO extends DBContext {
    
    private static ProductDAO instance;

    public ProductDAO() {
    }

    public static synchronized ProductDAO getInstance() {
        if (instance == null) {
            instance = new ProductDAO();
        }
        return instance;
    }

    public Product getProductById(int productId) {
        
        ProductColorDAO productColorDao = ProductColorDAO.getInstance();

        String sql = "SELECT [ProductID]\n"
                + "      ,[CategoryID]\n"
                + "      ,[ProductName]\n"
                + "      ,[Price]\n"
                + "      ,[Description]\n"
                + "      ,[CreatedAt]\n"
                + "FROM [dbo].[Product]\n"
                + "WHERE ProductID = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Product p = new Product();
                p.setProductID(rs.getInt("ProductID"));
                p.setCategoryID(rs.getInt("CategoryID"));
                p.setProductName(rs.getString("ProductName"));
                p.setPrice(rs.getDouble("Price"));
                p.setDescription(rs.getString("Description"));
                p.setCreatedAt(rs.getDate("CreatedAt"));

                p.setProductColors(productColorDao.getByProductId(productId));
                
                return p;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }
}
