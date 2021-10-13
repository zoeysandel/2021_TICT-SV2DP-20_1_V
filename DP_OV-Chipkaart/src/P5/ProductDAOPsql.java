package P5;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOPsql implements ProductDAO {
    private Connection conn;
    private OVChipkaartDAOPsql odao = null;

    public ProductDAOPsql(Connection conn) throws SQLException {
        this.conn = conn;
        this.odao = new OVChipkaartDAOPsql(conn);
    }

    @Override
    public boolean save(Product product) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO product VALUES (?, ?, ?, ?)");
            preparedStatement.setInt(1, product.getProductNummer());
            preparedStatement.setString(2, product.getNaam());
            preparedStatement.setString(3, product.getBeschrijving());
            preparedStatement.setFloat(4, product.getPrijs());
            preparedStatement.execute();

            PreparedStatement p1 = conn.prepareStatement("INSERT INTO ov_chipkaart_product VALUES (?, ?, ?, ?)");
            System.out.println(product.getOvChipkaart());
            for (OVChipkaart ovChipkaart : product.getOvChipkaart()) {
                p1.setInt(1, ovChipkaart.getKaartnummer());
                p1.setInt(2, product.getProductNummer());
                p1.setString(3, "gekocht");
                p1.setDate(4, java.sql.Date.valueOf(LocalDate.now()));
                p1.execute();
            }
        } catch (SQLException sqlException) {
            System.out.println("Couldn't save \n" + sqlException.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean update(Product product) {
        try {
            PreparedStatement stmt = conn.prepareStatement("UPDATE product SET product_nummer = ?, naam = ?, beschrijving = ?, prijs = ? WHERE product_nummer = ?");
            stmt.setInt(1, product.getProductNummer());
            stmt.setString(2, product.getNaam());
            stmt.setString(3, product.getBeschrijving());
            stmt.setFloat(4, product.getPrijs());
            stmt.setInt(5, product.getProductNummer());
            stmt.executeUpdate();

            stmt = conn.prepareStatement("DELETE FROM ov_chipkaart_product WHERE product_nummer = ?");
            stmt.setInt(1, product.getProductNummer());
            stmt.executeUpdate();


            stmt = conn.prepareStatement("INSERT INTO ov_chipkaart_product VALUES (?, ?, ?, ?)");
            for (OVChipkaart ovChipkaart : product.getOvChipkaart()) {
                stmt.setInt(1, ovChipkaart.getKaartnummer());
                stmt.setInt(2, product.getProductNummer());
                stmt.setString(3, "gekocht");
                stmt.setDate(4, Date.valueOf(LocalDate.now()));
                stmt.executeUpdate();
            }
        } catch (SQLException sqlException) {
            System.err.println("Couldn't update \n" + sqlException.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(Product product) {
        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM ov_chipkaart_product WHERE product_nummer = ?");
            stmt.setInt(1, product.getProductNummer());
            stmt.executeUpdate();

            stmt = conn.prepareStatement("DELETE FROM product WHERE product_nummer = ?");
            stmt.setInt(1, product.getProductNummer());
            stmt.executeUpdate();

        } catch (SQLException sqlException) {
            System.err.println("Couldn't delete\n" + sqlException.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public List<Product> findByOVChipkaart(OVChipkaart ovChipkaart) {
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT p.product_nummer, naam, beschrijving, prijs FROM ov_chipkaart_product ocp JOIN product p on ocp.product_nummer = p.product_nummer WHERE ocp.kaart_nummer = ?");
            stmt.setInt(1, ovChipkaart.getKaartnummer());
            ResultSet resultSet = stmt.executeQuery();

            ArrayList<Product> alleProducten = new ArrayList<>();

            if (resultSet.next()) {
                int nummer = resultSet.getInt("product_nummer");
                String naam = resultSet.getString("naam");
                String beschrijving = resultSet.getString("beschrijving");
                float prijs = resultSet.getFloat("prijs");
                Product product = new Product(nummer, naam, beschrijving, prijs);
                alleProducten.add(product);
            }

            return alleProducten;
        } catch (SQLException sqlException) {
            System.err.println("Couldn't find product with ovchip" + ovChipkaart.toString());
            return null;
        }
    }

    @Override
    public List<Product> findAll() {
        List<Product> alleProducten = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM product;");
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                int productNummer = resultSet.getInt("product_nummer");
                String naam = resultSet.getString("naam");
                String beschrijving = resultSet.getString("beschrijving");
                float prijs = resultSet.getFloat("prijs");
                Product product = new Product(productNummer, naam, beschrijving, prijs);
                alleProducten.add(product);

                List<OVChipkaart> ovChipkaarten= this.odao.findByProduct(product);
                product.setOvChipkaart(ovChipkaarten);
            }
        } catch (SQLException sqlException) {
            System.err.println("Er heeft zich een fout opgetreden in findAll");
            return null;
        }
        return alleProducten;
    }
}