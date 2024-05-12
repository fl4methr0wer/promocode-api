package pl.lodz.sii.promocodeapi.core.repository;

import pl.lodz.sii.promocodeapi.core.model.Product;
import java.util.List;
import java.util.Optional;

public interface ProductRepo {
    Optional<Product> findById(Long id);
    List<Product> readAll();
    Optional<Long> save(Product product);
    boolean deleteById(Long id);
    Optional<Product> update(Product product);
}
