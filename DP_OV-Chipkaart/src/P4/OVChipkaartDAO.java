package P4;

import java.util.List;

public interface OVChipkaartDAO {
    public boolean create(OVChipkaart ovchipkaart);
    public boolean update(OVChipkaart ovchipkaart);
    public boolean delete(OVChipkaart ovchipkaart);

    public List<OVChipkaart> findByReiziger(Reiziger reiziger);
}
