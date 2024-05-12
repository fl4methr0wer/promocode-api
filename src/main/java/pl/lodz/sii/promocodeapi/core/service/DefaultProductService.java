package pl.lodz.sii.promocodeapi.core.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.lodz.sii.promocodeapi.core.model.Product;
import pl.lodz.sii.promocodeapi.core.repository.ProductRepo;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DefaultProductService implements ProductService {

    private ProductRepo productRepo;

    @Override
    public List<Product> readAll() {
        return productRepo.readAll();
    }

    @Override
    public Optional<Product> read(Long id) {
        return productRepo.findById(id);
    }

    @Override
    public Optional<Long> save(Product product) {
        return productRepo.save(product);
    }

    @Override
    public Optional<Product> update(Product product) {
        return productRepo.update(product);
    }

    @Override
    public boolean deleteById(Long id) {
        return productRepo.deleteById(id);
    }
}
