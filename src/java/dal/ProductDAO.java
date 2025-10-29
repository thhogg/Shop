/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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

    public List<Product> getAllProducts() {
        ProductColorDAO productColorDao = ProductColorDAO.getInstance();
        List<Product> list = new ArrayList<>();
        String sql = """
                     SELECT [ProductID]
                           ,[CategoryID]
                           ,[ProductName]
                           ,[Price]
                           ,[Description]
                           ,[CreatedAt]
                     FROM [dbo].[Product]""";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product p = new Product();
                int productId = rs.getInt("ProductID");
                p.setProductID(productId);
                p.setCategoryID(rs.getInt("CategoryID"));
                p.setProductName(rs.getString("ProductName"));
                p.setPrice(rs.getInt("Price"));
                p.setDescription(rs.getString("Description"));
                p.setCreatedAt(rs.getDate("CreatedAt"));

                p.setProductColors(productColorDao.getByProductId(productId));

                list.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public Product getProductById(int productId) {
        ProductColorDAO productColorDao = ProductColorDAO.getInstance();
        String sql = """
                     SELECT [ProductID]
                           ,[CategoryID]
                           ,[ProductName]
                           ,[Price]
                           ,[Description]
                           ,[CreatedAt]
                     FROM [dbo].[Product]
                     WHERE ProductID = ?""";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Product p = new Product();
                p.setProductID(rs.getInt("ProductID"));
                p.setCategoryID(rs.getInt("CategoryID"));
                p.setProductName(rs.getString("ProductName"));
                p.setPrice(rs.getInt("Price"));
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

    public int insertProduct(int categoryId, String productName, int price, String description) {
        String sql = """
                     INSERT INTO [dbo].[Product]
                                ([CategoryID]
                                ,[ProductName]
                                ,[Price]
                                ,[Description]
                                ,[CreatedAt])
                     OUTPUT INSERTED.ProductID
                     VALUES
                     (?,?,?,?,GETDATE())""";

        int newProductId = -1;

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, categoryId);
            ps.setString(2, productName);
            ps.setInt(3, price);
            ps.setString(4, description);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                newProductId = rs.getInt("ProductID");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return newProductId;
    }

    public void updateProduct(int productId, int subCategoryId, String productName, int price, String description) {
        String sql = """
                     UPDATE [dbo].[Product]
                     SET [CategoryID] = ?
                           ,[ProductName] = ?
                           ,[Price] = ?
                           ,[Description] = ?
                     WHERE ProductID = ?""";
        
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, subCategoryId);
            ps.setString(2, productName);
            ps.setInt(3, price);
            ps.setString(4, description);
            ps.setInt(5, productId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }

    }
}
