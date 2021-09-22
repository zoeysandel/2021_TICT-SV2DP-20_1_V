package P4;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOPsql implements ReizigerDAO
{
    private Connection conn;
    private AdresDAO adao;
    private OVChipkaartDAO odao;

    public ReizigerDAOPsql(Connection conn)
    {
        this.conn = conn;
    }

    public ReizigerDAOPsql(Connection conn, AdresDAO adao, OVChipkaartDAO odao)
    {
        this.conn = conn;
        this.adao = adao;
        this.odao = odao;
    }

    public void setAdao(AdresDAO adao) {
        this.adao = adao;
    }

    public void setOdao(OVChipkaartDAO odao) {
        this.odao = odao;
    }

    @Override
    public boolean create(Reiziger reiziger)
    {
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

            if (reiziger.getAdres() != null) {
                this.adao.create(reiziger.getAdres());
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean update(Reiziger reiziger)
    {
        try {
            String sql = "UPDATE reiziger SET "
                    + "voorletters = ?, "
                    + "tussenvoegsel = ?, "
                    + "achternaam = ?, "
                    + "geboortedatum = ? "
                    + "WHERE reiziger_id = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, reiziger.getVoorletters());
            stmt.setString(2, reiziger.getTussenvoegsel());
            stmt.setString(3, reiziger.getAchternaam());
            stmt.setDate(4, Date.valueOf(reiziger.getGeboortedatum()));
            stmt.setInt(5, reiziger.getId());
            stmt.executeUpdate();

            if (reiziger.getAdres() != null) {
                this.adao.update(reiziger.getAdres());
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(Reiziger reiziger)
    {
        try {
            String sql = "DELETE FROM reiziger WHERE reiziger_id = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, reiziger.getId());

            if (reiziger.getAdres() != null) {
                this.adao.delete(reiziger.getAdres());
                stmt.executeUpdate();
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public Reiziger findById(Integer id)
    {
        Reiziger r = new Reiziger();
        try {
            String sql = "SELECT * FROM reiziger WHERE reiziger_id = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet res = stmt.executeQuery();

            if (res.next()) {
                int reiziger_id = res.getInt(
                        "reiziger_id"
                );
                String voorletters = res.getString(
                        "voorletters"
                );
                String tussenvoegsel = res.getString(
                        "tussenvoegsel"
                );
                String achternaam = res.getString(
                        "achternaam"
                );
                LocalDate geboortedatum = res.getDate(
                        "geboortedatum"
                ).toLocalDate();

                r = new Reiziger(
                        reiziger_id,
                        voorletters,
                        tussenvoegsel,
                        achternaam,
                        geboortedatum
                );

                Adres a = adao.findByReiziger(r);
                r.setAdres(a);
                return r;
            }
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return r;
    }

    @Override
    public List<Reiziger> findAll()
    {
        List<Reiziger> reizigers = new ArrayList<>();
        List<Adres> adressen = new ArrayList<>();

        try {
            String sql = "SELECT * FROM reiziger, adres";

            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                Reiziger r = new Reiziger
                        (
                        res.getInt("reiziger_id"),
                        res.getString("voorletters"),
                        res.getString("tussenvoegsel"),
                        res.getString("achternaam"),
                        res.getDate("geboortedatum").toLocalDate());
                reizigers.add(r);
            }
            this.adao.findAll();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return reizigers;
    }
}
