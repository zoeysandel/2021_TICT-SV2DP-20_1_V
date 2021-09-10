package P3;

public class Adres {
    private Integer adres_id;
    private String postcode;
    private String huisnummer;
    private String straat;
    private String woonplaats;
    private Integer reiziger_id;

    public Adres() {}

    public Adres(Integer adres_id, String postcode, String huisnummer, String straat, String woonplaats, Integer reiziger_id)
    {
        this.adres_id = adres_id;
        this.postcode = postcode;
        this.huisnummer = huisnummer;
        this.straat = straat;
        this.woonplaats = woonplaats;
        this.reiziger_id = reiziger_id;
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

    public String getHuisnummer()
    {
        return huisnummer;
    }

    public void setHuisnummer(String huisnummer)
    {
        if (huisnummer != null) {
            this.huisnummer = huisnummer;
        }
    }

    public String getStraat()
    {
        return straat;
    }

    public void setStraat(String straat)
    {
        if (straat != null) {
            this.straat = straat;
        }
    }

    public String getWoonplaats()
    {
        return woonplaats;
    }

    public void setWoonplaats(String woonplaats)
    {
        if (woonplaats != null) {
            this.woonplaats = woonplaats;
        }
    }

    public Integer getReiziger_id()
    {
        return reiziger_id;
    }

    public void setReiziger_id(Integer reiziger_id)
    {
        if (reiziger_id != null) {
            this.reiziger_id = reiziger_id;
        }
    }

    public String getPostcode()
    {
        return postcode;
    }

    public void setPostcode(String pc)
    {
        if (pc != null) {
            this.postcode = pc;
        }
    }

    public String toString()
    {
        return "Reiziger met id: " + this.getReiziger_id() + " woont op: " + this.getStraat() + " " + this.getHuisnummer() + " met postcode " + this.getPostcode() + " te, " + this.getWoonplaats() ;
    }
}
