package P5;

import java.util.List;

public interface ProductDAO
{
    boolean save(Product product);
    boolean update(Product product);
    boolean delete(Product product);
    public List<Product> findByOVChipkaart(OVChipkaart ovChipkaart);
    public List<Product> findAll();
}