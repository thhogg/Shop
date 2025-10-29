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
import model.entity.Category;

/**
 *
 * @author Leo
 */
public class CategoryDAO extends DBContext {

    private static CategoryDAO instance;

    public CategoryDAO() {
    }

    public static synchronized CategoryDAO getInstance() {
        if (instance == null) {
            instance = new CategoryDAO();
        }
        return instance;
    }

    /*
    Get Cateogry By ParentId
    1: Women
    2: Men
    3: Kids
    4: Accessories
    5: Cosmetic
     */
    public List<Category> getCategoriesByParentId(int parentId) {
        List<Category> list = new ArrayList<>();
        String sql = "select * from Category where ParentID = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, parentId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Category c = new Category();
                c.setCategoryId(rs.getInt("CategoryID"));
                c.setCategoryName(rs.getString("CategoryName"));
                c.setParentId(rs.getInt("ParentID"));
                list.add(c);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public Category getCategoryById(int categoryId) {
        String sql = """
                     SELECT [CategoryID]
                           ,[CategoryName]
                           ,[ParentID]
                     FROM [dbo].[Category]
                     WHERE CategoryID = ?""";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, categoryId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Category c = new Category();
                c.setCategoryId(rs.getInt("CategoryID"));
                c.setCategoryName(rs.getString("CategoryName"));
                c.setParentId(rs.getInt("ParentID"));
                return c;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }
}
