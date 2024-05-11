package pl.lodz.sii.promocodeapi.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.lodz.sii.promocodeapi.persistence.entity.PurchaseEntity;

public interface PurchaseJPARepo extends JpaRepository<PurchaseEntity, Long> {
}
