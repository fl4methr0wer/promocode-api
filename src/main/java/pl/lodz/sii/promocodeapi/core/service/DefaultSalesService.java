package pl.lodz.sii.promocodeapi.core.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.lodz.sii.promocodeapi.core.exception.ObjectNotFoundException;
import pl.lodz.sii.promocodeapi.core.exception.PromoCodeException;
import pl.lodz.sii.promocodeapi.core.model.PriceQuotation;
import pl.lodz.sii.promocodeapi.core.model.Product;
import pl.lodz.sii.promocodeapi.core.model.PromoCode;
import pl.lodz.sii.promocodeapi.core.model.Purchase;
import pl.lodz.sii.promocodeapi.core.repository.PurchaseRepo;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DefaultSalesService implements SalesService {

    private final PurchaseRepo purchaseRepo;
    private final ProductService productService;
    private final PromoCodeService promoCodeService;

    @Override
    public PriceQuotation calculateDiscount(Long productId, String promoCodeValue)
            throws PromoCodeException, ObjectNotFoundException {

        Optional<Product> product = productService.read(productId);
        Optional<PromoCode> promoCode = promoCodeService.read(promoCodeValue);
        if (product.isEmpty()) {
            throw new ObjectNotFoundException("Product not found");
        }
        if (promoCode.isEmpty()) {
            throw new PromoCodeException("Promo code does not exist");
        }
        return new PriceQuotation(product.get(), promoCode.get());
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
