/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.util.ArrayList;
import java.util.List;
import model.entity.ProductColor;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Leo
 */
public class ProductColorDAO extends DBContext {
    
    private static ProductColorDAO instance;

    public ProductColorDAO() {
    }

    public static synchronized ProductColorDAO getInstance() {
        if (instance == null) {
            instance = new ProductColorDAO();
        }
        return instance;
    }

    public List<ProductColor> getByProductId(int productId) {
        List<ProductColor> list = new ArrayList<>();
        ColorDAO colorDao = ColorDAO.getInstance();
        ProductImageDAO productImageDao = ProductImageDAO.getInstance();
        ProductVariantDAO productVariantDao = ProductVariantDAO.getInstance();
        
        String sql = "SELECT [ProductColorID]\n"
                + "      ,[ProductID]\n"
                + "      ,[ColorID]\n"
                + "FROM [dbo].[ProductColor]\n"
                + "WHERE ProductID = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ProductColor pc = new ProductColor();
                
                int productColorId = rs.getInt("ProductColorID");
                int colorId = rs.getInt("ColorID");
                
                pc.setProductColorID(productColorId);
                pc.setProductID(rs.getInt("ProductID"));
                pc.setColorID(colorId);
                
                pc.setColor(colorDao.getColorByColorId(colorId));
                pc.setImages(productImageDao.getByProductColorId(productColorId));
                pc.setVariants(productVariantDao.getByProductColorId(productColorId));
                
                list.add(pc);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return list;
    }
}
