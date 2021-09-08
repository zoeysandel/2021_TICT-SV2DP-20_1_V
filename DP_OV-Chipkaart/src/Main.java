import javax.xml.transform.Result;
import java.sql.*;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws SQLException {
        int counter = 0;

        String db = "jdbc:postgresql://localhost:5432/ovchip";

        Properties props = new Properties();
        props.setProperty("user","postgres");
        props.setProperty("password","root");

        Connection conn = DriverManager.getConnection(db, props);

        Statement stmt = conn.createStatement();
        ResultSet res = stmt.executeQuery("SELECT * FROM reiziger");

        System.out.println("Alle reizigers: ");
        while (res.next()) {
            counter++;
            System.out.println(
                    "#" + counter + " " + res.getString("voorletters") + ". " + res.getString("achternaam") + " " + "(" + res.getString("geboortedatum") + ")");
        }
    }
}