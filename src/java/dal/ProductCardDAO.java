/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.util.ArrayList;
import java.util.List;
import model.dto.ProductCard;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Leo
 */
public class ProductCardDAO extends DBContext {
    
    private static ProductCardDAO instance;

    public ProductCardDAO() {
    }

    public static synchronized ProductCardDAO getInstance() {
        if (instance == null) {
            instance = new ProductCardDAO();
        }
        return instance;
    }

    //PRODUCT CARD
    public List<ProductCard> getAllProductCards() {
        List<ProductCard> list = new ArrayList<>();
        String sql = """
                     SELECT p.ProductID, p.ProductName, p.Price, i.imageUrl AS MainImageUrl, p.CategoryID, pc.ProductColorID
                     FROM Product p
                     JOIN ProductColor pc ON p.productId = pc.productId
                     JOIN ProductImage i ON pc.productColorId = i.productColorId AND i.Main = 1""";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ProductCard p = new ProductCard(
                        rs.getInt("ProductID"),
                        rs.getString("ProductName"),
                        rs.getDouble("Price"),
                        rs.getString("MainImageUrl"),
                        rs.getInt("CategoryID"),
                        rs.getInt("ProductColorID")
                );
                list.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public List<ProductCard> getProductCardsByParentID(int parentID) {
        List<ProductCard> list = new ArrayList<>();
        String sql = """
                     SELECT p.ProductID, p.ProductName, p.Price, i.imageUrl AS MainImageUrl, p.CategoryID, pc.ProductColorID
                     FROM Product p
                     JOIN ProductColor pc ON p.productId = pc.productId
                     JOIN ProductImage i ON pc.productColorId = i.productColorId AND i.Main = 1
                     JOIN Category c on c.CategoryID = p.CategoryID
                     WHERE c.ParentID = ?""";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, parentID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ProductCard p = new ProductCard();
                p.setProductID(rs.getInt("ProductID"));
                p.setProductName(rs.getString("ProductName"));
                p.setPrice(rs.getDouble("Price"));
                p.setMainImage(rs.getString("mainImageUrl"));
                p.setCategoryId(rs.getInt("CategoryID"));
                p.setProductColorId(rs.getInt("ProductColorID"));
                list.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public List<ProductCard> getProductCardsByCategoryID(int categoryID) {
        List<ProductCard> list = new ArrayList<>();
        String sql = """
                     SELECT p.ProductID, p.ProductName, p.Price, i.imageUrl AS MainImageUrl, p.CategoryID, pc.ProductColorID
                     FROM Product p
                     JOIN ProductColor pc ON p.productId = pc.productId
                     JOIN ProductImage i ON pc.productColorId = i.productColorId AND i.Main = 1
                     WHERE p.CategoryID = ?""";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, categoryID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ProductCard p = new ProductCard(
                        rs.getInt("ProductID"),
                        rs.getString("ProductName"),
                        rs.getDouble("Price"),
                        rs.getString("mainImageUrl"),
                        rs.getInt("CategoryID"),
                        rs.getInt("ProductColorID")
                );
                list.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public List<ProductCard> getProductCardsByPrice(int from, int to) {
        List<ProductCard> list = new ArrayList<>();
        String sql = """
                     SELECT p.ProductID, p.ProductName, p.Price, i.imageUrl AS MainImageUrl, p.CategoryID, pc.ProductColorID
                     FROM Product p
                     JOIN ProductColor pc ON p.productId = pc.productId
                     JOIN ProductImage i ON pc.productColorId = i.productColorId AND i.Main = 1
                     where p.Price >= ?  and p.Price <= ?""";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, from);
            ps.setInt(2, to);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ProductCard p = new ProductCard(
                        rs.getInt("ProductID"),
                        rs.getString("ProductName"),
                        rs.getDouble("Price"),
                        rs.getString("MainImageUrl"),
                        rs.getInt("CategoryID"),
                        rs.getInt("ProductColorID")
                );
                list.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

}
