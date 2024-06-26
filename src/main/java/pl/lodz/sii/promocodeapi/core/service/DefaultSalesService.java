package pl.lodz.sii.promocodeapi.core.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.lodz.sii.promocodeapi.core.exception.ObjectNotFoundException;
import pl.lodz.sii.promocodeapi.core.exception.PromoCodeException;
import pl.lodz.sii.promocodeapi.core.model.*;
import pl.lodz.sii.promocodeapi.core.repository.PurchaseRepo;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DefaultSalesService implements SalesService {

    private final PurchaseRepo purchaseRepo;
    private final ProductService productService;
    private final PromoCodeService promoCodeService;

    private static final String PRODUCT_NOT_FOUND_ERROR_MESSAGE = "Prduct not found";

    @Override
    public PriceQuotation calculateDiscount(Long productId, String promoCodeValue)
            throws PromoCodeException, ObjectNotFoundException {

        Optional<Product> product = productService.read(productId);
        Optional<PromoCode> promoCode = promoCodeService.read(promoCodeValue);
        if (product.isEmpty()) {
            throw new ObjectNotFoundException(PRODUCT_NOT_FOUND_ERROR_MESSAGE);
        }
        if (promoCode.isEmpty()) {
            throw new PromoCodeException("Promo code does not exist");
        }
        return new PriceQuotation(product.get(), promoCode.get());
    }

    @Override
    public Optional<Purchase> makePurchase(Long productId) throws ObjectNotFoundException {
        Optional<Product> product = productService.read(productId);
        if (product.isEmpty()) {
            throw new ObjectNotFoundException(PRODUCT_NOT_FOUND_ERROR_MESSAGE);
        }
        Purchase purchase = new Purchase(product.get());
        return purchaseRepo.save(purchase);
    }

    @Override
    public Optional<Purchase> makePurchaseWithPromoCode(Long productId, String promoCode) throws ObjectNotFoundException, PromoCodeException {
        Optional<Product> product = productService.read(productId);
        if (product.isEmpty()) {
            throw new ObjectNotFoundException(PRODUCT_NOT_FOUND_ERROR_MESSAGE);
        }
        Optional<PromoCode> promo = promoCodeService.read(promoCode);
        if (promo.isEmpty()) {
            throw new PromoCodeException("Promo code does not exist");
        }
        Purchase purchase = new Purchase(product.get(), promo.get());
        return purchaseRepo.save(purchase);
    }

    @Override
    public List<Purchase> getAllPurchases() {
        return purchaseRepo.readAll();
    }

    @Override
    public Optional<Purchase> getById(Long id) {
        return purchaseRepo.findById(id);
    }

    @Override
    public List<SalesReportRecord> getSalesReport() {
        Map<Currency, List<Purchase>> purchasesGroupedByCurrency = purchaseRepo.readAll()
                .stream()
                .collect(Collectors.groupingBy(purchase -> purchase.getRegularPrice().getCurrency()));

        return purchasesGroupedByCurrency.entrySet()
                .stream()
                .map(entry -> {
                    Currency currency = entry.getKey();
                    List<Purchase> purchases = entry.getValue();
                    BigDecimal totalAmount = BigDecimal.ZERO;
                    BigDecimal totalDiscount = BigDecimal.ZERO;
                    for (Purchase purchase : purchases) {
                        totalAmount = totalAmount.add(purchase.getTotalPrice().getValue());
                        BigDecimal discount = purchase.getRegularPrice().getValue()
                                .subtract(purchase.getTotalPrice().getValue());
                        totalDiscount = totalDiscount.add(discount);
                    }
                    Integer numberOfPurchases = purchases.size();
                    return new SalesReportRecord(currency, totalAmount, totalDiscount, numberOfPurchases);
                })
                .toList();
    }
}
