package pl.lodz.sii.promocodeapi.persistence.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.sii.promocodeapi.core.model.Product;
import pl.lodz.sii.promocodeapi.core.repository.ProductRepo;
import pl.lodz.sii.promocodeapi.persistence.entity.ProductEntity;
import pl.lodz.sii.promocodeapi.persistence.mapper.ProductMapper;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class ProductRepoJPAAdapter implements ProductRepo {

    private final ProductEntityJPARepo repo;
    private final ProductMapper mapper;

    @Override
    public Optional<Product> findById(Long id) {
        Optional<ProductEntity> entity = repo.findById(id);
        if (entity.isEmpty()) {
            return Optional.empty();
        }
        Product product = mapper.toModel(entity.get());
        return Optional.of(product);
    }

    @Override
    public List<Product> readAll() {
        return repo.findAll().stream()
                .map(mapper::toModel)
                .toList();
    }

    @Override
    public Optional<Long> save(Product product) {
        ProductEntity entity = repo.save(mapper.toEntity(product));
        return Optional.of(mapper.toModel(entity).getId());
    }

    @Transactional
    @Override
    public Optional<Product> update(Product product) {
        Optional<ProductEntity> existingOptionalProductEntity = repo.findById(product.getId());
        if (existingOptionalProductEntity.isEmpty()) {
            return Optional.empty();
        }
        ProductEntity saved = repo.saveAndFlush(mapper.toEntity(product));
        return Optional.of(mapper.toModel(saved));
    }

    @Override
    public boolean deleteById(Long id) {
        Optional<ProductEntity> entity = repo.findById(id);
        if (entity.isEmpty()) {
            return false;
        }
        repo.delete(entity.get());
        return true;
    }
}
