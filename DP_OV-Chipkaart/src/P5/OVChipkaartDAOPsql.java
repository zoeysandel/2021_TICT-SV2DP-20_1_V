package P5;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OVChipkaartDAOPsql implements OVChipkaartDAO {
    private Connection conn;
    private ReizigerDAO rdao;
    private ProductDAO pdao;

    public OVChipkaartDAOPsql(Connection conn) {
        this.conn = conn;
    }

    public OVChipkaartDAOPsql(Connection conn, ReizigerDAO rdao)
    {
        this.conn = conn;
        this.rdao = rdao;
    }

    public void setRdao(ReizigerDAO rdao)
    {
        this.rdao = rdao;
    }

    public void setPdao(ProductDAOPsql pdao) {
        this.pdao = pdao;
    }

    @Override
    public boolean save(OVChipkaart ovchipkaart) {
        try {
            String sql = "INSERT INTO ov_chipkaart "
                    + "(kaart_nummer, geldig_tot, klasse, saldo, reiziger_id) "
                    + "VALUES(?, ?, ?, ?, ?)";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, ovchipkaart.getKaartnummer());
            stmt.setDate(2, Date.valueOf(ovchipkaart.getGeldigTot()));
            stmt.setInt(3, ovchipkaart.getKlasse());
            stmt.setDouble(4, ovchipkaart.getSaldo());
            stmt.setInt(5, ovchipkaart.getReizigerId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean update(OVChipkaart ovchipkaart) {
        try {
            String sql = "UPDATE ov_chipkaart SET "
                    + "kaart_nummer = ?, "
                    + "geldig_tot = ?, "
                    + "klasse = ?, "
                    + "saldo = ? "
                    + "WHERE reiziger_id = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, ovchipkaart.getKaartnummer());
            stmt.setDate(2, Date.valueOf(ovchipkaart.getGeldigTot()));
            stmt.setInt(3, ovchipkaart.getKlasse());
            stmt.setDouble(4, ovchipkaart.getSaldo());
            stmt.setInt(5, ovchipkaart.getReizigerId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(OVChipkaart ovchipkaart) {
        try {
            String sql = "DELETE FROM ov_chipkaart WHERE reiziger_id = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, ovchipkaart.getReizigerId());

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) {
        List<OVChipkaart> ovchipkaarten = new ArrayList<>();

        try {
            String sql = "SELECT * FROM ov_chipkaart INNER JOIN reiziger ON ov_chipkaart.reiziger_id = reiziger.reiziger_id";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet res = stmt.executeQuery();

            while (res.next()) {
               OVChipkaart o = new OVChipkaart
                       (
                       res.getInt("kaart_nummer"),
                       res.getDate("geldig_tot").toLocalDate(),
                       res.getInt("klasse"),
                       res.getDouble("saldo"),
                       res.getInt("reiziger_id")
                       );
               ovchipkaarten.add(o);
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return ovchipkaarten;
    }

    @Override
    public List<OVChipkaart> findByProduct(Product product) {
        List<OVChipkaart> ovchipkaarten = new ArrayList<>();
        try {
            String sql = "SELECT * FROM ov_chipkaart o INNER JOIN ov_chipkaart_product ON o.kaart_nummer = ov_chipkaart_product.kaart_nummer WHERE product_nummer = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, product.getProductNummer());
            ResultSet res = stmt.executeQuery();

            while (res.next()) {
                OVChipkaart o = new OVChipkaart
                        (
                                res.getInt("kaart_nummer"),
                                res.getDate("geldig_tot").toLocalDate(),
                                res.getInt("klasse"),
                                res.getDouble("saldo"),
                                res.getInt("reiziger_id")
                        );
                ovchipkaarten.add(o);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return ovchipkaarten;
    }

    @Override
    public List<OVChipkaart> findAll() {
        List<OVChipkaart> ovchipkaarten = new ArrayList<>();
        try {
            String sql = "SELECT * FROM ov_chipkaart";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet res = stmt.executeQuery();

            while (res.next()) {
                OVChipkaart o = new OVChipkaart
                        (
                                res.getInt("kaart_nummer"),
                                res.getDate("geldig_tot").toLocalDate(),
                                res.getInt("klasse"),
                                res.getDouble("saldo"),
                                res.getInt("reiziger_id")
                        );
                ovchipkaarten.add(o);

                List<Product> producten = pdao.findByOVChipkaart(o);
                o.setProducten(producten);
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return ovchipkaarten;
    }
}
