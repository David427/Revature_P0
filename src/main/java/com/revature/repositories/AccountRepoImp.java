package com.revature.repositories;

import com.revature.models.CheckingAccount;
import com.revature.models.SavingsAccount;
import com.revature.util.JDBCConnection;
import com.revature.util.LinkedList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountRepoImp implements AccountRepo {

    Connection conn = JDBCConnection.getConnection();

    @Override
    public void addChecking(CheckingAccount c) {
        String sql = "INSERT INTO c_accounts VALUES (default, ?, ?, ?)";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, c.getOwnerId());
            ps.setString(2, c.getCheckingName());
            ps.setDouble(3, c.getCheckingBalance());
            ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public CheckingAccount getChecking(int id) {
        String sql = "SELECT * FROM c_accounts WHERE c_id = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return buildChecking(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public LinkedList<CheckingAccount> getAllChecking() {
        String sql = "SELECT * FROM c_accounts";

        try {
            LinkedList<CheckingAccount> checkingAccs = new LinkedList<>();

            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                checkingAccs.add(buildChecking(rs));
            }
            return checkingAccs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateChecking(CheckingAccount change) {
        String sql = "UPDATE c_accounts SET owner_id = ?, c_name = ?, c_balance = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, change.getOwnerId());
            ps.setString(2, change.getCheckingName());
            ps.setDouble(3, change.getCheckingBalance());
            ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteChecking(int id) {
        String sql = "DELETE FROM c_accounts WHERE c_id = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addSavings(SavingsAccount s) {
        String sql = "INSERT INTO s_accounts VALUES (default, ?, ?, ?)";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, s.getOwnerId());
            ps.setString(2, s.getSavingsName());
            ps.setDouble(3, s.getSavingsBalance());
            ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public SavingsAccount getSavings(int id) {
        String sql = "SELECT * FROM s_accounts WHERE s_id = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return buildSavings(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public LinkedList<SavingsAccount> getAllSavings() {
        String sql = "SELECT * FROM s_accounts";

        try {
            LinkedList<SavingsAccount> savingsAccs = new LinkedList<>();

            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                savingsAccs.add(buildSavings(rs));
            }
            return savingsAccs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateSavings(SavingsAccount change) {
        String sql = "UPDATE s_accounts SET owner_id = ?, s_name = ?, s_balance = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, change.getOwnerId());
            ps.setString(2, change.getSavingsName());
            ps.setDouble(3, change.getSavingsBalance());
            ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteSavings(int id) {
        String sql = "DELETE FROM s_accounts WHERE s_id = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Helper methods.
    private CheckingAccount buildChecking(ResultSet rs) throws SQLException {
        CheckingAccount c = new CheckingAccount();
        c.setCheckingId(rs.getInt("c_id"));
        c.setCheckingName(rs.getString("c_name"));
        c.setCheckingBalance(rs.getDouble("c_balance"));
        return c;
    }

    private SavingsAccount buildSavings(ResultSet rs) throws SQLException {
        SavingsAccount s = new SavingsAccount();
        s.setSavingsId(rs.getInt("s_id"));
        s.setSavingsName(rs.getString("s_name"));
        s.setSavingsBalance(rs.getDouble("s_balance"));
        return s;
    }
}
