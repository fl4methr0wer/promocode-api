package pl.lodz.sii.promocodeapi.core.service;

import pl.lodz.sii.promocodeapi.core.model.Product;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> readAll();
    Optional<Product> read(Long id);
    Optional<Long> save(Product product);
    Optional<Product> update(Product product);
    boolean deleteById(Long id);
}
