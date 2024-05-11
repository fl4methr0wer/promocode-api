package pl.lodz.sii.promocodeapi.core.repository;

import java.util.List;
import java.util.Optional;
import pl.lodz.sii.promocodeapi.core.model.Purchase;

public interface PurchaseRepo {
    Optional<Purchase> findById(Long id);
    List<Purchase> readAll();
    Optional<Purchase> save(Purchase purchase);
}
