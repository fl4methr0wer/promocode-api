package pl.lodz.sii.promocodeapi.core.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.lodz.sii.promocodeapi.core.exception.PromoCodeException;
import pl.lodz.sii.promocodeapi.core.exception.ValidationException;
import java.time.LocalDate;
import java.util.Optional;

@Data
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class Purcase implements Validatable {
    Long id;
    LocalDate date;
    Product product;
    Optional<PromoCode> promoCode;
    Price regularPrice;
    Price discountPrice;

    public Purcase(Product product) {
        this.date = LocalDate.now();
        this.product = product;
        this.promoCode = Optional.empty();
        this.regularPrice = product.getPrice();
        this.discountPrice = product.getPrice();
    }

    public Purcase(Product product, PromoCode promoCode) {
        this.date = LocalDate.now();
        this.product = product;
        this.regularPrice = product.getPrice();
        this.discountPrice = product.getPrice();
        if (promoCode == null) {
            this.promoCode = Optional.empty();
        } else {
            this.promoCode = Optional.of(promoCode);
            try {
                this.discountPrice = promoCode.applyTo(product);
            } catch (PromoCodeException e) {
                this.discountPrice = this.regularPrice;
            }
        }
        }

    @Override
    public void validate() throws ValidationException {
        if (date == null) {
            throw new ValidationException("Date is required");
        }
    }
}
