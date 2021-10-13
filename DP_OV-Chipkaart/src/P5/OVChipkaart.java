package P5;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OVChipkaart {
    private Integer kaart_nummer;
    private LocalDate geldig_tot;
    private Integer klasse;
    private double saldo;
    private Integer reiziger_id;
    private Reiziger reiziger;

    private List<Product> producten = new ArrayList<>();

    public OVChipkaart() { }

    public OVChipkaart(Integer kaart_nummer, LocalDate geldig_tot, Integer klasse, double saldo, Integer reiziger_id) {
        this.kaart_nummer = kaart_nummer;
        this.geldig_tot = geldig_tot;
        this.klasse = klasse;
        this.saldo = saldo;
        this.reiziger_id = reiziger_id;
    }

    public Reiziger getReiziger() {
        return this.reiziger;
    }

    public void setReiziger(Reiziger reiziger) {
        if (reiziger != null) {
            this.reiziger = reiziger;
        }
    }

    public Integer getKaartnummer() {
        return this.kaart_nummer;
    }

    public LocalDate getGeldigTot() {
        return this.geldig_tot;
    }

    public Integer getKlasse() {
        return this.klasse;
    }

    public double getSaldo() {
        return this.saldo;
    }

    public int getReizigerId() {
        return this.reiziger_id;
    }

    public String toString() {
        return "OVChipkaart met nummer: "
                + this.getKaartnummer()
                + " kan worden gebruikt om te reizen met "
                + this.getKlasse()
                + "e klasse en is geldig tot en met "
                + this.getGeldigTot() + ". "
                + "Momenteel heeft de ovchipkaart een saldo van "
                + this.getSaldo() +
                " euro.";
    }

    public void setProducten(List<Product> producten) {
        if (!producten.isEmpty()) {
            this.producten = producten;
        }
    }
}
