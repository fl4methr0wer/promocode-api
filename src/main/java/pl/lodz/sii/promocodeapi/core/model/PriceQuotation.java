package pl.lodz.sii.promocodeapi.core.model;

import lombok.*;
import pl.lodz.sii.promocodeapi.core.exception.PromoCodeException;
import pl.lodz.sii.promocodeapi.core.exception.ValidationException;

import java.math.BigDecimal;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class PriceQuotation implements Validatable<PriceQuotation> {
    private Product product;
    private Optional<PromoCode> promoCode;
    private Price price;
    private String warning = "";

    @Override
    public void validate() throws ValidationException {
        if (product == null) {
            throw new ValidationException("Product is null");
        }
        if (promoCode.isPresent()) {
            promoCode.get().getDiscount().validate();
        }
        price.validate();
    }

    public PriceQuotation(Product product) {
        this.product = product;
        this.promoCode = Optional.empty();
        this.price = product.getPrice();
        this.warning = null;
    }
    public PriceQuotation(Product product, PromoCode promoCode) {
        this.product = product;
        this.promoCode = Optional.of(promoCode);
        applyPromoCode();
    }

    private void applyPromoCode() {
        if (this.promoCode.isEmpty()) {
            return;
        }
        applyPromoOrAddWarningIfNotPossible();
        setPriceToZeroIfInvalid();
    }

    private void applyPromoOrAddWarningIfNotPossible() {
        try {
            this.price = product.getPrice();
            this.price = promoCode.get().applyTo(this.product);
        } catch (PromoCodeException e) {
            this.warning = e.getMessage();
        }
    }

    private void setPriceToZeroIfInvalid() {
        try {
            this.price.validate();
        } catch (ValidationException e) {
            this.price = new Price(BigDecimal.ZERO, this.price.getCurrency());
        }
    }
}
