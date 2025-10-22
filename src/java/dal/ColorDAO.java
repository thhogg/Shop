/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.util.ArrayList;
import java.util.List;
import model.entity.Color;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Leo
 */
public class ColorDAO extends DBContext {
    
    private static ColorDAO instance;

    public ColorDAO() {
    }

    public static synchronized ColorDAO getInstance() {
        if (instance == null) {
            instance = new ColorDAO();
        }
        return instance;
    }

    public List<Color> getAllColors() {
        List<Color> list = new ArrayList<>();
        String sql = "SELECT [ColorID]\n"
                + "      ,[ColorName]\n"
                + "      ,[HexCode]\n"
                + "FROM [dbo].[Color]";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Color s = new Color(rs.getInt("ColorID"),
                        rs.getString("ColorName"),
                        rs.getString("HexCode"));
                list.add(s);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return list;
    }

    public Color getColorByColorId(int colorId) {
        String sql = "SELECT [ColorID]\n"
                + "      ,[ColorName]\n"
                + "      ,[HexCode]\n"
                + "FROM [dbo].[Color]\n"
                + "WHERE ColorID = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, colorId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Color c = new Color();
                c.setId(rs.getInt("ColorID"));
                c.setName(rs.getString("ColorName"));
                c.setHexCode(rs.getString("HexCode"));
                return c;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return null;
    }

}
