package pl.lodz.sii.promocodeapi.core.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
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
    PromoCode product;
    Optional<PromoCode> promoCode;
    Price regularPrice;
    Price discountPrice;

    @Override
    public void validate() throws ValidationException {
        if (date == null) {
            throw new ValidationException("Date is required");
        }
    }
}
