package pl.lodz.sii.promocodeapi.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.lodz.sii.promocodeapi.persistence.entity.ProductEntity;

public interface ProductEntityJPARepo extends JpaRepository<ProductEntity, Long> {
}
