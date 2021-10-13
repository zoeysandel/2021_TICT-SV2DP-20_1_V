package P5;

import java.util.List;

public interface OVChipkaartDAO
{
    public boolean save(OVChipkaart ovchipkaart);
    public boolean update(OVChipkaart ovchipkaart);
    public boolean delete(OVChipkaart ovchipkaart);

    public List<OVChipkaart> findByReiziger(Reiziger reiziger);
    public List<OVChipkaart> findByProduct(Product product);
    public List<OVChipkaart> findAll();
}
