package pl.lodz.sii.promocodeapi.persistence.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.lodz.sii.promocodeapi.core.model.Purchase;
import pl.lodz.sii.promocodeapi.core.repository.PurchaseRepo;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class PurchaseRepoJPAAdapter implements PurchaseRepo {

    @Override
    public Optional<Purchase> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Purchase> readAll() {
        return List.of();
    }

    @Override
    public Optional<Purchase> save(Purchase purchase) {
        return Optional.empty();
    }
}
