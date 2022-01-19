package com.revature.repositories;

import com.revature.models.User;
import com.revature.util.JDBCConnection;
import com.revature.util.LinkedList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepoImp implements UserRepo {

    Connection conn = JDBCConnection.getConnection();

    @Override
    public void addUser(User u) {
        String sql = "INSERT INTO users VALUES (default, ?, ?)";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, u.getUserLogin());
            ps.setString(2, u.getUserPassword());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User getUser(int id) {
        String sql = "SELECT * FROM users WHERE u_id = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return buildUser(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User findUser(String name) {
        String sql = "SELECT * FROM users WHERE u_login = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return buildUser(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public LinkedList<User> getAllUsers() {
        String sql = "SELECT * FROM users";

        try {
            LinkedList<User> users = new LinkedList<>();

            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                users.add(buildUser(rs));
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateUser(User change) {
        String sql = "UPDATE users SET u_login = ?, u_password = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, change.getUserLogin());
            ps.setString(2, change.getUserPassword());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteUser(int id) {
        String sql = "DELETE FROM users WHERE u_id = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //region HELPER METHODS
    private User buildUser(ResultSet rs) throws SQLException {
        User u = new User();
        u.setUserId(rs.getInt("u_id"));
        u.setUserLogin(rs.getString("u_login"));
        u.setUserPassword(rs.getString("u_password"));
        return u;
    }
    //endregion
}
