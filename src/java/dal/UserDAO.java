/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import model.entity.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Leo
 */
public class UserDAO extends DBContext{
    private static UserDAO instance;

    public UserDAO() {
    }

    public static synchronized UserDAO getInstance() {
        if (instance == null) {
            instance = new UserDAO();
        }
        return instance;
    }
    
    public User getUserByNameAndPassword(String username, String password) {
        String sql = """
                     SELECT [UserID]
                           ,[UserName]
                           ,[Password]
                           ,[FullName]
                           ,[Email]
                           ,[Phone]
                           ,[Address]
                           ,[Role]
                           ,[CreatedAt]
                     FROM [FashionShop].[dbo].[Users]
                     WHERE UserName = ? AND Password = ?""";
        
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User u = new User();
                u.setUserID(rs.getInt("UserID"));
                u.setUserName(rs.getString("UserName"));
                u.setRole(rs.getInt("Role"));
                return u;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        return null;
    }
}
