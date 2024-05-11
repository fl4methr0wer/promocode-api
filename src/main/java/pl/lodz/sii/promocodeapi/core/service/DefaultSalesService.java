package pl.lodz.sii.promocodeapi.core.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.lodz.sii.promocodeapi.core.exception.ObjectNotFoundException;
import pl.lodz.sii.promocodeapi.core.exception.PromoCodeException;
import pl.lodz.sii.promocodeapi.core.model.PriceQuotation;
import pl.lodz.sii.promocodeapi.core.repository.PurchaseRepo;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DefaultSalesService implements SalesService {

    private final PurchaseRepo purchaseRepo;

    @Override
    public PriceQuotation calculateDiscount(Long productId, String promoCode) throws PromoCodeException, ObjectNotFoundException {
        return null;
    }

    @Override
    public Optional<Purchase> makePurchase(Long productId) throws ObjectNotFoundException {
        return Optional.empty();
    }

    @Override
    public Optional<Purchase> makePurchaseWithPromoCode(Long productId, String promoCode) throws ObjectNotFoundException, PromoCodeException {
        return Optional.empty();
    }

    @Override
    public List<Purchase> getAllPurchases() {
        return List.of();
    }

    @Override
    public Optional<Purchase> getById(Long id) {
        return Optional.empty();
    }
}
