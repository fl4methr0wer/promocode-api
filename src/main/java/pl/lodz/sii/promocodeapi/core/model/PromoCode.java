package pl.lodz.sii.promocodeapi.core.model;

import lombok.*;
import java.time.LocalDate;
import pl.lodz.sii.promocodeapi.core.exception.PromoCodeException;
import pl.lodz.sii.promocodeapi.core.exception.ValidationException;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class PromoCode implements Validatable {
    private String code;
    private LocalDate expires;
    private Price discount;
    private Integer maximumUsages = 1;
    private Integer hasBeenUsedTimes = 0;

    @Override
    public void validate() throws ValidationException {
        if (code == null || code.isEmpty() || code.length() < 3 || code.length() > 24) {
            throw new ValidationException("PromoCode length should be 3-24 characters");
        }
        if (code.matches(".*\\s.*")) {
            throw new ValidationException("PromoCode should not contain whitespace");
        }
        if (expires == null) {
            throw new ValidationException("Expiration date must not be null");
        }
        if (discount == null) {
            throw new ValidationException("Discount must not be null");
        }
        discount.validate();
    }

    public Price applyTo(Product product) throws PromoCodeException {
        try {
            if (expires.isBefore(LocalDate.now())) {
                throw new ValidationException("Expired promo code cannot be applied");
            }
            if (this.getHasBeenUsedTimes() >= maximumUsages) {
                throw new ValidationException("Promo code exceeded maximum usages");
            }
            return product.getPrice().subtract(this.discount);
        } catch (ValidationException e) {
            throw new PromoCodeException(e.getMessage());
        }
    }
}
