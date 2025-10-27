/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.util.ArrayList;
import java.util.List;
import model.entity.ProductVariant;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Leo
 */
public class ProductVariantDAO extends DBContext {

    private static ProductVariantDAO instance;

    public ProductVariantDAO() {
    }

    public static synchronized ProductVariantDAO getInstance() {
        if (instance == null) {
            instance = new ProductVariantDAO();
        }
        return instance;
    }

    public List<ProductVariant> getByProductColorId(int productColorID) {
        List<ProductVariant> list = new ArrayList<>();
        SizeDAO sizeDao = SizeDAO.getInstance();

        String sql = "SELECT [ProductVariantID]\n"
                + "      ,[ProductColorID]\n"
                + "      ,[SizeID]\n"
                + "      ,[Quantity]\n"
                + "FROM [dbo].[ProductVariant]\n"
                + "WHERE ProductColorID = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, productColorID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ProductVariant pv = new ProductVariant();

                int sizeId = rs.getInt("SizeID");

                pv.setProductVariantID(rs.getInt("ProductVariantID"));
                pv.setProductColorID(rs.getInt("ProductColorID"));
                pv.setSizeID(sizeId);
                pv.setQuantity(rs.getInt("Quantity"));

                pv.setSize(sizeDao.getSizeBySizeId(sizeId));

                list.add(pv);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public void insertProductVariant(int productColorId, int sizeId, int quantity) {
        String sql = """
                     INSERT INTO [dbo].[ProductVariant]
                                ([ProductColorID]
                                ,[SizeID]
                                ,[Quantity])
                    VALUES
                     (?,?,?)""";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, productColorId);
            ps.setInt(2, sizeId);
            ps.setInt(3, quantity);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
