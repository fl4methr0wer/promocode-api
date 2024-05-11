package pl.lodz.sii.promocodeapi.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.lodz.sii.promocodeapi.persistence.entity.PromoCodeEntity;

public interface PromoCodeEntityJPARepo extends JpaRepository<PromoCodeEntity, String> {
}
