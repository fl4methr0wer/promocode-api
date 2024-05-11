package pl.lodz.sii.promocodeapi.core.service;

import pl.lodz.sii.promocodeapi.core.exception.ObjectNotFoundException;
import pl.lodz.sii.promocodeapi.core.exception.PromoCodeException;
import pl.lodz.sii.promocodeapi.core.model.PriceQuotation;
import pl.lodz.sii.promocodeapi.core.model.Purchase;
import java.util.List;
import java.util.Optional;

public interface SalesService {
    PriceQuotation calculateDiscount(Long productId, String promoCode) throws PromoCodeException, ObjectNotFoundException;
    Optional<Purchase> makePurchase(Long productId) throws ObjectNotFoundException;
    Optional<Purchase> makePurchaseWithPromoCode(Long productId, String promoCode) throws ObjectNotFoundException, PromoCodeException;
    List<Purchase> getAllPurchases();
    Optional<Purchase> getById(Long id);
}
