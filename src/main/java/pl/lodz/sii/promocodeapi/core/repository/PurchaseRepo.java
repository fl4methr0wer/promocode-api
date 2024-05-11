package pl.lodz.sii.promocodeapi.core.repository;

import java.util.List;
import java.util.Optional;

public interface PurchaseRepo {
    Optional<Purchase> findById(Long id);
    List<Purchase> readAll();
    Optional<Purchase> save(Purchase purchase);
}
