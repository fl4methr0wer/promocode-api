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
public class Purchase implements Validatable {
    Long id;
    LocalDate date;
    Product product;
    Optional<PromoCode> promoCode;
    Price regularPrice;
    Price totlaPrice;

    public Purchase(Product product) {
        this.date = LocalDate.now();
        this.product = product;
        this.promoCode = Optional.empty();
        this.regularPrice = product.getPrice();
        this.totlaPrice = product.getPrice();
    }

    public Purchase(Product product, PromoCode promoCode) {
        this.date = LocalDate.now();
        this.product = product;
        this.regularPrice = product.getPrice();
        this.totlaPrice = product.getPrice();
        if (promoCode == null) {
            this.promoCode = Optional.empty();
        } else {
            this.promoCode = Optional.of(promoCode);
            try {
                this.totlaPrice = promoCode.applyTo(product);
            } catch (PromoCodeException e) {
                this.promoCode = Optional.empty();
                this.totlaPrice = this.regularPrice;
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
