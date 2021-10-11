package P5;

import java.util.List;

public interface AdresDAO
{
    public boolean create(Adres adres);
    public boolean update(Adres adres);
    public boolean delete(Adres adres);

    public List<Adres> findAll();
    public Adres findById(Integer id);
    public Adres findByReiziger(Reiziger reiziger);
}
