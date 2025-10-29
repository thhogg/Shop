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
import model.dto.ProductColorData;

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

        String sql = """
                     SELECT [ProductColorID]
                           ,[ProductID]
                           ,[ColorID]
                     FROM [dbo].[ProductColor]
                     WHERE ProductID = ?""";

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

    public int insertProductColor(int productId, int colorId) {
        String sql = """
                     INSERT INTO [dbo].[ProductColor]
                                ([ProductID]
                                ,[ColorID])
                     OUTPUT INSERTED.ProductColorID
                     VALUES
                     (?,?)""";

        int newProductColorId = -1;

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, productId);
            ps.setInt(2, colorId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                newProductColorId = rs.getInt("ProductColorID");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return newProductColorId;
    }

    public void deleteByProductId(int productId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public List<ProductColorData> getProductColorDatasByProductId(int productId) {
        List<ProductColorData> list = new ArrayList<>();
        ColorDAO colorDao = ColorDAO.getInstance();
        ProductImageDAO productImageDao = ProductImageDAO.getInstance();
        ProductVariantDAO productVariantDao = ProductVariantDAO.getInstance();

        String sql = """
                     SELECT [ProductColorID]
                           ,[ProductID]
                           ,[ColorID]
                     FROM [dbo].[ProductColor]
                     WHERE ProductID = ?""";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ProductColorData pcd = new ProductColorData();

                int productColorId = rs.getInt("ProductColorID");
                int colorId = rs.getInt("ColorID");

                pcd.setColorId(colorId);

                pcd.setImageUrls(productImageDao.getProductImageDataByProductColorId(productColorId));
                pcd.setSizes(productVariantDao.getProductSizeDataByProductColorId(productColorId));

                list.add(pcd);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return list;
    }
}
