package pl.lodz.sii.promocodeapi.core.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import java.time.LocalDate;
import pl.lodz.sii.promocodeapi.core.exception.ValidationException;

@Data
@ToString
@EqualsAndHashCode
public class PromoCode implements Validatable<PromoCode> {
    private String code;
    private LocalDate expires;
    private Price discount;
    private Integer maximumUsages;
    private Integer hasBeenUsedTimes;

    @Override
    public void validate() throws ValidationException {
        if (expires == null) {
            throw new ValidationException("Expiration date must not be null");
        }
        if (discount == null) {
            throw new ValidationException("Discount must not be null");
        }
        discount.validate();
        if (hasBeenUsedTimes > maximumUsages) {
            throw new ValidationException("Maximum usages exceeded");
        }
    }
}
