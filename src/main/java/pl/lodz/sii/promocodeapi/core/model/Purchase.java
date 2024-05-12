package pl.lodz.sii.promocodeapi.core.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.lodz.sii.promocodeapi.core.exception.PromoCodeException;
import pl.lodz.sii.promocodeapi.core.exception.ValidationException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Data
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class Purchase implements Validatable {
    Long id;
    LocalDate date;
    Product product;
    Optional<PromoCode> promoCode;
    Price regularPrice;
    Price totalPrice;

    public Purchase(Product product) {
        this.date = LocalDate.now();
        this.product = product;
        this.promoCode = Optional.empty();
        this.regularPrice = product.getPrice();
        this.totalPrice = product.getPrice();
    }

    public Purchase(Product product, PromoCode promoCode) {
        this.date = LocalDate.now();
        this.product = product;
        this.regularPrice = product.getPrice();
        this.totalPrice = product.getPrice();
        applyPromoCode(promoCode);
    }

    private void applyPromoCode(PromoCode promoCode) {
        if (promoCode == null) {
            this.promoCode = Optional.empty();
            return;
        }
        this.promoCode = Optional.of(promoCode);
        try {
            this.totalPrice = promoCode.applyTo(product);
            this.totalPrice = changePriceEqualToZeroIfIsNegative(this.totalPrice);
        } catch (PromoCodeException e) {
            this.promoCode = Optional.empty();
            this.totalPrice = this.regularPrice;
        }
    }

    private Price changePriceEqualToZeroIfIsNegative(Price price) {
        return price.getValue().compareTo(BigDecimal.ZERO) < 0 ?
                new Price(BigDecimal.ZERO, price.getCurrency())
                : price;
    }

    @Override
    public void validate() throws ValidationException {
        if (date == null) {
            throw new ValidationException("Date is required");
        }
        if (product == null) {
            throw new ValidationException("Product is required");
        }
    }
}
