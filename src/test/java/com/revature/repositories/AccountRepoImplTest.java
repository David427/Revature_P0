package com.revature.repositories;

import com.revature.models.CheckingAccount;
import com.revature.models.SavingsAccount;
import com.revature.models.Transaction;
import com.revature.util.JDBCConnection;
import com.revature.util.LinkedList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountRepoImplTest implements AccountRepoTest {

    Connection conn = JDBCConnection.getConnection();

    @Override
    public void addChecking(CheckingAccount c) {
        String sql = "INSERT INTO c_accounts VALUES (default, ?, ?, ?)";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, c.getOwnerId());
            ps.setString(2, c.getCheckingName());
            ps.setDouble(3, c.getCheckingBalance());
            ps.executeUpdate();
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
    public CheckingAccount getCheckingByOwner(int id) {
        CheckingAccount acct = new CheckingAccount();
        String sql = "SELECT * FROM c_accounts WHERE owner_id = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                acct = buildChecking(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return acct;
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
        String sql = "UPDATE c_accounts SET c_name = ?, c_balance = ? WHERE c_id = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, change.getCheckingName());
            ps.setDouble(2, change.getCheckingBalance());
            ps.setInt(3, change.getCheckingId());
            ps.executeUpdate();
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
            ps.executeUpdate();
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
            ps.executeUpdate();
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
    public SavingsAccount getSavingsByOwner(int id) {
        SavingsAccount acct = new SavingsAccount();
        String sql = "SELECT * FROM s_accounts WHERE owner_id = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                acct = (buildSavings(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return acct;
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
        String sql = "UPDATE s_accounts SET s_name = ?, s_balance = ? WHERE s_id = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, change.getSavingsName());
            ps.setDouble(2, change.getSavingsBalance());
            ps.setInt(3, change.getSavingsId());
            ps.executeUpdate();
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
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addTransaction(Transaction t) {
        String sql = "INSERT INTO transactions VALUES (default, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, t.getOwnerId());
            ps.setString(2, t.getType());
            ps.setString(3, t.getFromAccount());
            ps.setString(4, t.getToAccount());
            ps.setDouble(5, t.getAmount());
            ps.setLong(6, t.getTimestamp());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public LinkedList<Transaction> getCheckingTransactions(int id) {
        String sql = "SELECT * FROM transactions WHERE owner_id = ? AND (t_from_account = 'Checking' OR t_to_account = 'Checking')";

        try {
            LinkedList<Transaction> transactions = new LinkedList<>();

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                transactions.add(buildTransaction(rs));
            }
            return transactions;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public LinkedList<Transaction> getSavingsTransactions(int id) {
        String sql = "SELECT * FROM transactions WHERE owner_id = ? AND (t_from_account = 'Savings' OR t_to_account = 'Savings')";

        try {
            LinkedList<Transaction> transactions = new LinkedList<>();

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                transactions.add(buildTransaction(rs));
            }
            return transactions;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public LinkedList<Transaction> getAllTransactions(int id) {
        String sql = "SELECT * FROM transactions WHERE owner_id = ?";

        try {
            LinkedList<Transaction> transactions = new LinkedList<>();

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                transactions.add(buildTransaction(rs));
            }
            return transactions;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //region HELPER METHODS
    private CheckingAccount buildChecking(ResultSet rs) throws SQLException {
        CheckingAccount c = new CheckingAccount();
        c.setCheckingId(rs.getInt("c_id"));
        c.setOwnerId(rs.getInt("owner_id"));
        c.setCheckingName(rs.getString("c_name"));
        c.setCheckingBalance(rs.getDouble("c_balance"));
        return c;
    }

    private SavingsAccount buildSavings(ResultSet rs) throws SQLException {
        SavingsAccount s = new SavingsAccount();
        s.setSavingsId(rs.getInt("s_id"));
        s.setOwnerId(rs.getInt("owner_id"));
        s.setSavingsName(rs.getString("s_name"));
        s.setSavingsBalance(rs.getDouble("s_balance"));
        return s;
    }

    private Transaction buildTransaction(ResultSet rs) throws SQLException {
        Transaction t = new Transaction();
        t.setTransactionId(rs.getInt("t_id"));
        t.setOwnerId(rs.getInt("owner_id"));
        t.setType(rs.getString("t_type"));
        t.setFromAccount(rs.getString("t_from_account"));
        t.setToAccount(rs.getString("t_to_account"));
        t.setAmount(rs.getDouble("t_amount"));
        t.setTimestamp(rs.getLong("t_timestamp"));
        return t;
    }
    //endregion
}
