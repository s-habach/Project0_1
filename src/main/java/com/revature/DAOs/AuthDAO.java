package com.revature.DAOs;

import com.revature.utils.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AuthDAO {

    public boolean login(String username, String password) {

        // Open a connection
        try (Connection conn = ConnectionUtil.getConnection()) {

            // Create our SQL String
            String sql = "SELECT * FROM admins WHERE username = ? AND " +
                    "password = ?;";

            // PreparedStatement and fill in the blanks
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, username);
            ps.setString(2, password);


            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return true;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Couldn't login user.");
        }

        return false;
    }
}
