package P3;

import java.util.List;

public interface ReizigerDAO
{
    public boolean create(Reiziger reiziger);
    public boolean update(Reiziger reiziger);
    public boolean delete(Reiziger reiziger);

    public List<Reiziger> findAll();
    public Reiziger findById(Integer id);
}
