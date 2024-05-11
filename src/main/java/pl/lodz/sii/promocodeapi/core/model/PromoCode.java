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
        if (expires == null || expires.isBefore(LocalDate.now())) {
            throw new ValidationException("Expiration date must not be null and must be after now");
        }
        if (discount == null) {
            throw new ValidationException("Discount must not be null");
        }
        discount.validate();
        if (hasBeenUsedTimes != null && maximumUsages != null && hasBeenUsedTimes > maximumUsages) {
            throw new ValidationException("Maximum usages exceeded");
        }
    }

    public Price applyTo(Product product) throws PromoCodeException {
        try {
            return product.getPrice().subtract(this.discount);
        } catch (ValidationException e) {
            throw new PromoCodeException(e.getMessage());
        }
    }
}
