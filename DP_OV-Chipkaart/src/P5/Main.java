package P5;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws SQLException
    {
        Connection conn = null;

        try {
            conn = Main.getConnection();
            System.out.println("Database connected");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ReizigerDAOPsql rdao = new ReizigerDAOPsql(conn);


            OVChipkaartDAOPsql odao = new OVChipkaartDAOPsql(conn, rdao);

            ProductDAOPsql pdao = new ProductDAOPsql(conn);

            testProduct(pdao, rdao, odao);

            conn.close();
        }
    }

    private static Connection getConnection() throws SQLException
    {
        String db = "jdbc:postgresql://localhost:5432/ovchip";

        Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", "root");

        Connection conn = DriverManager.getConnection(db, props);
        conn.setAutoCommit(true);
        return conn;
    }

    public static void testProduct(ProductDAO pdao, ReizigerDAO rdao, OVChipkaartDAO odao) {
        System.out.println("findAll");
        System.out.println("---------------------");

        List<Product> producten = pdao.findAll();
        for (Product p : producten) {
            System.out.println(p);
        }

        Reiziger reiziger = new Reiziger(9732, "Z", "", "Sandel", LocalDate.of(2002, 9, 19));
        OVChipkaart ovChipkaart = new OVChipkaart(99999, LocalDate.of(2029,9,5), 3, 112.99f, reiziger.getId());
        OVChipkaart ovChipkaart1 = new OVChipkaart(99998, LocalDate.of(2027,6,12), 2, 20.56f, reiziger.getId());
        Product product = new Product(123, "test", "testproduct", 10.00f);

        System.out.println("save\n");

        System.out.println("save reiziger");
        rdao.save(reiziger);
        System.out.println("save ovchipkaart");
        odao.save(ovChipkaart);
        odao.save(ovChipkaart1);

        product.getOvChipkaart().add(ovChipkaart);
        product.getOvChipkaart().add(ovChipkaart1);

        System.out.println("save product\n");
        pdao.save(product);

        System.out.println(pdao.findAll());

        System.out.println("---------------------");



        System.out.println("update");

        System.out.println("voor update");
        System.out.println(pdao.findByOVChipkaart(ovChipkaart));

        product.setNaam("Na update");
        pdao.update(product);

        System.out.println("na update");
        System.out.println(pdao.findByOVChipkaart(ovChipkaart));

        System.out.println("---------------------");


        System.out.println("delete");
        pdao.delete(product);
        System.out.println(pdao.findByOVChipkaart(ovChipkaart));
        System.out.println(pdao.findAll());
        System.out.println("---------------------");
    }
}