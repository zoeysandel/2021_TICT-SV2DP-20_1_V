package P4;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdresDAOPsql implements AdresDAO
{
    Connection conn;
    ReizigerDAO rdao;

    public AdresDAOPsql(Connection conn, ReizigerDAO rdao)
    {
        this.conn = conn;
        this.rdao = rdao;
    }

    @Override
    public boolean create(Adres adres)
    {
        try {
            String sql = "INSERT INTO adres "
                    + "(adres_id, postcode, huisnummer, straat, woonplaats, reiziger_id) "
                    + "VALUES(?, ?, ?, ?, ?, ?)";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, adres.getAdres_id());
            stmt.setString(2, adres.getPostcode());
            stmt.setString(3, adres.getHuisnummer());
            stmt.setString(4, adres.getStraat());
            stmt.setString(5, adres.getWoonplaats());
            stmt.setInt(6, adres.getReiziger_id());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean update(Adres adres)
    {
        try {
            String sql = "UPDATE adres SET "
                    + "postcode = ?, "
                    + "huisnummer = ?, "
                    + "straat = ?, "
                    + "woonplaats = ? "
                    + "WHERE adres_id = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, adres.getPostcode());
            stmt.setString(2, adres.getHuisnummer());
            stmt.setString(3, adres.getStraat());
            stmt.setString(4, adres.getWoonplaats());
            stmt.setInt(5, adres.getAdres_id());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(Adres adres)
    {
        try {
            String sql = "DELETE FROM adres WHERE adres_id = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, adres.getAdres_id());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public Adres findById(Integer id) {
        return null;
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger)
    {
        Adres a = new Adres();
        try {
            String sql = "SELECT * FROM adres WHERE reiziger_id = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, reiziger.getId());
            ResultSet res = stmt.executeQuery();

            if (res.next()) {
                Integer adres_id = res.getInt("adres_id");
                String postcode = res.getString("postcode");
                String huisnummer = res.getString("huisnummer");
                String straat = res.getString("straat");
                String woonplaats = res.getString("woonplaats");
                Integer reiziger_id = res.getInt("reiziger_id");

                a = new Adres(adres_id, postcode, huisnummer, straat, woonplaats, reiziger_id);
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return a;
    }

    public List<Adres> findAll()
    {
        List<Adres> adressen = new ArrayList<>();

        try {
            String sql = "SELECT * FROM adres";

            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                Adres a = new Adres(res.getInt("adres_id"),
                        res.getString("postcode"),
                        res.getString("huisnummer"),
                        res.getString("straat"),
                        res.getString("woonplaats"),
                        res.getInt("reiziger_id"));
                adressen.add(a);
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return adressen;
    }
}
