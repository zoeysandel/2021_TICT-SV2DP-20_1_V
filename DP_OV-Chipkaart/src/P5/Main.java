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

            AdresDAOPsql adao = new AdresDAOPsql(conn, rdao);

            OVChipkaartDAOPsql odao = new OVChipkaartDAOPsql(conn, rdao);
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
}