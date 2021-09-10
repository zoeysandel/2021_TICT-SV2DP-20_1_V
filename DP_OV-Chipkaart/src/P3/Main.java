package P3;

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
//            testReizigerDAO(rdao);

            AdresDAOPsql adao = new AdresDAOPsql(conn, rdao);
            rdao.setAdao(adao);
            testAdresDAO(adao, rdao);
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

    /**
     * P2. Reiziger DAO: persistentie van een klasse
     * <p>
     * Deze methode test de CRUD-functionaliteit van de Reiziger DAO
     *
     * @throws SQLException
     */

    private static void testReizigerDAO(ReizigerDAO rdao) throws SQLException
    {
        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        // String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", LocalDate.of(1981, 3, 14));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.create() ");
        rdao.create(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        // Sietske komt er achter dat zij een andere achternaam blijkt te hebben en wil deze natuurlijk veranderen
        sietske.setAchternaam("Pietersen");
        rdao.update(sietske);

        // Met de findById methode controleren we of haar achternaam daadwerkelijk is veranderd
        Reiziger r = rdao.findById(77);
        System.out.println(r);
        System.out.println();

        System.out.println("Momenteel bevat de database: " + reizigers.size() + " reizigers");
        rdao.delete(sietske);
        reizigers = rdao.findAll();
        System.out.println("Na delete bevat de database: " + reizigers.size() + " reizigers");
    }

    private static void testAdresDAO(AdresDAO adao, ReizigerDAO rdao) throws SQLException
    {
        System.out.println("\n---------- Test AdresDAO -------------");

        // Haal alle reizigers op uit de database
        List<Adres> adressen = adao.findAll();
        System.out.println("[Test] AdresDAO.findAll() geeft de volgende adressen:");
        for (Adres a : adressen) {
            System.out.println(a);
        }
        System.out.println();

        // Zoek adres door middel van reiziger
        Adres adres = new Adres(100, "3521", "45", "Van der Goesstraat", "Utrecht", 88);
        Reiziger zoey = new Reiziger(88, "Z", "", "Sandel", LocalDate.of(2002, 9, 19), adres);

        // Create reiziger en adres
        rdao.create(zoey);
//        adao.create(adres);

        // findByReiziger
        System.out.println(adao.findByReiziger(zoey));

        // Delete reiziger en adres
        rdao.delete(zoey);
        System.out.println();
        System.out.println("Reiziger " + zoey.getNaam() + " is verwijderd.");
        System.out.println("Adres met straatnaam " + zoey.getAdres().getStraat() + " is verwijderd.");
    }
}