package P4;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Reiziger {
    private Integer id;
    private String voorletters;
    private String tussenvoegsel;
    private String achternaam;
    private LocalDate geboortedatum;
    private Integer adres_id;
    private Adres adres;
    private Integer kaartnummer;

    private List<OVChipkaart> ovchipkaarten = new ArrayList<>();

    public Reiziger() {}

    public Reiziger(int id, String voorletters, String tussenvoegsel, String achternaam, LocalDate geboortedatum)
    {
        this.id = id;
        this.voorletters = voorletters;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
        this.geboortedatum = geboortedatum;
    }

    public Reiziger(int id, String voorletters, String tussenvoegsel, String achternaam, LocalDate geboortedatum, Adres adres)
    {
        this.id = id;
        this.voorletters = voorletters;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
        this.geboortedatum = geboortedatum;
        this.adres = adres;
    }

    public Reiziger(int id, String voorletters, String tussenvoegsel, String achternaam, LocalDate geboortedatum, Adres adres, List<OVChipkaart> ovchipkaarten)
    {
        this.id = id;
        this.voorletters = voorletters;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
        this.geboortedatum = geboortedatum;
        this.adres = adres;
        this.ovchipkaarten = ovchipkaarten;
    }

    public Integer getKaartnummer() {
        return kaartnummer;
    }

    public void setKaartnummer(Integer kaartnummer) {
        if (kaartnummer != null) {
            this.kaartnummer = kaartnummer;
        }
    }

    public Adres getAdres()
    {
        return adres;
    }

    public List<OVChipkaart> getOVChipkaarten() {
        return ovchipkaarten;
    }

    public int getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        if (id != null) {
            this.id = id;
        }
    }

    public String getNaam()
    {
        return Objects.equals(this.tussenvoegsel, "") ? voorletters + " " + achternaam : voorletters + " " + tussenvoegsel + " " + achternaam;
    }

    public LocalDate getGeboortedatum()
    {
        return geboortedatum;
    }

    public void setGeboortedatum(LocalDate geboortedatum)
    {
        if (geboortedatum != null) {
            this.geboortedatum = geboortedatum;
        }
    }

    public String toString()
    {
        return "Reiziger met id: " + this.getId() + " heet: " + this.getNaam() + " en is geboren op " + this.getGeboortedatum() + ".";
    }

    public String getVoorletters()
    {
        return voorletters;
    }

    public String getAchternaam()
    {
        return achternaam;
    }

    public void setAchternaam(String achternaam)
    {
        if (achternaam != null) {
            this.achternaam = achternaam;
        }
    }

    public void setTussenvoegsel(String tussenvoegsel)
    {
        if (tussenvoegsel != null) {
            this.tussenvoegsel = tussenvoegsel;
        }
    }

    public Integer getAdres_id()
    {
        return adres_id;
    }

    public void setAdres_id(Integer adres_id)
    {
        if (adres_id != null) {
            this.adres_id = adres_id;
        }
    }

    public String getTussenvoegsel()
    {
        return tussenvoegsel;
    }

    public void setAdres(Adres adres) {
        if (adres != null) {
            this.adres = adres;
        }
    }

    public void setOVChipkaart(OVChipkaart ovchipkaart) {
        if (ovchipkaart != null) {
            this.ovchipkaarten.add(ovchipkaart);
        }
    }
}
