package pl.lodz.sii.promocodeapi.persistence.repository;

import org.springframework.stereotype.Repository;
import pl.lodz.sii.promocodeapi.core.model.Product;
import pl.lodz.sii.promocodeapi.core.repository.ProductRepo;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepoJPAAdapter implements ProductRepo {

    @Override
    public Optional<Product> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Product> readAll() {
        return List.of();
    }

    @Override
    public Optional<Long> save(Product product) {
        return Optional.empty();
    }

    @Override
    public boolean deleteById(Long id) {
        return false;
    }

    @Override
    public boolean update(Product product) {
        return false;
    }
}
