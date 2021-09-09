package P2;
import java.time.LocalDate;
import java.util.Objects;

public class Reiziger {
    private Integer id;
    private String voorletters;
    private String tussenvoegsel;
    private String achternaam;
    private LocalDate geboortedatum;

    public Reiziger(int id, String voorletters, String tussenvoegsel, String achternaam, LocalDate geboortedatum)
    {
        this.id = id;
        this.voorletters = voorletters;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
        this.geboortedatum = geboortedatum;
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

    public String getTussenvoegsel()
    {
        return tussenvoegsel;
    }
}
