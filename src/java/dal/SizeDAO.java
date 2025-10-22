/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.util.ArrayList;
import java.util.List;
import model.entity.Size;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Leo
 */
public class SizeDAO extends DBContext {
    
    private static SizeDAO instance;

    public SizeDAO() {
    }

    public static synchronized SizeDAO getInstance() {
        if (instance == null) {
            instance = new SizeDAO();
        }
        return instance;
    }
    
    public List<Size> getAllSizes() {
        List<Size> list = new ArrayList<>();
        String sql = "SELECT [SizeID]\n"
                + "      ,[SizeName]\n"
                + "FROM [dbo].[Size]";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Size s = new Size(rs.getInt("SizeID"),
                        rs.getString("SizeName"));
                list.add(s);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return list;
    }

    public Size getSizeBySizeId(int sizeId) {
        String sql = "SELECT [SizeID]\n"
                + "      ,[SizeName]\n"
                + "FROM [dbo].[Size]\n"
                + "WHERE SizeID = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, sizeId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Size s = new Size(rs.getInt("SizeID"),
                        rs.getString("SizeName"));
                return s;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return null;
    }
}
