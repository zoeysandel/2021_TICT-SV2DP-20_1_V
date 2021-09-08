package P2;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        String db = "jdbc:postgresql://localhost:5432/ovchip";

        Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", "root");

        try {
            Connection conn = DriverManager.getConnection(db, props);
            ReizigerDAO rdao = new ReizigerDAOPsql(conn);
            testReizigerDAO(rdao);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * P2. Reiziger DAO: persistentie van een klasse
     *
     * Deze methode test de CRUD-functionaliteit van de Reiziger DAO
     *
     * @throws SQLException
     */

    private static void testReizigerDAO (ReizigerDAO rdao) throws SQLException {
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
}