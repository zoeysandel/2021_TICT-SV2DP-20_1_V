package P2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOPsql implements ReizigerDAO {
    Connection conn;

    public ReizigerDAOPsql(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean create(Reiziger reiziger) {
        try {
            String sql = "INSERT INTO reiziger "
                    + "(reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum) "
                    + "VALUES(?, ?, ?, ?, ?)";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, reiziger.getId());
            stmt.setString(2, reiziger.getVoorletters());
            stmt.setString(3, reiziger.getTussenvoegsel());
            stmt.setString(4, reiziger.getAchternaam());
            stmt.setDate(5, Date.valueOf(reiziger.getGeboortedatum()));
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean update(Reiziger reiziger) {
        try {
            String sql = "UPDATE reiziger SET "
                    + "voorletters = ? "
                    + "tussenvoegsel = ? "
                    + "achternaam = ? "
                    + "geboortedatum = ? "
                    + "WHERE reiziger_id = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, reiziger.getVoorletters());
            stmt.setString(2, reiziger.getTussenvoegsel());
            stmt.setString(3, reiziger.getAchternaam());
            stmt.setDate(4, Date.valueOf(reiziger.getGeboortedatum()));
            stmt.setInt(5, reiziger.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(Reiziger reiziger) {
        try {
            String sql = "DELETE reizigers WHERE reiziger_id = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, reiziger.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public List<Reiziger> findAll() {
        List<Reiziger> reizigers = new ArrayList<>();

        try {
            String sql = "SELECT * FROM reiziger";

            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                Reiziger r = new Reiziger(res.getInt("reiziger_id"),
                        res.getString("voorletters"),
                        res.getString("tussenvoegsel"),
                        res.getString("achternaam"),
                        res.getDate("geboortedatum").toLocalDate());
                reizigers.add(r);
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return reizigers;
    }
}
