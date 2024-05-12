package pl.lodz.sii.promocodeapi.core.model;

import lombok.*;
import pl.lodz.sii.promocodeapi.core.exception.ValidationException;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@ToString
@EqualsAndHashCode
public class Price implements Validatable {
    private BigDecimal value = BigDecimal.ZERO;
    private Currency currency;

    public Price(BigDecimal value, Currency currency) {
        this.value = value.setScale(2, RoundingMode.CEILING);
        this.currency = currency;
    }

    @Override
    public void validate() throws ValidationException {
        if (this.value == null || this.currency == null) {
            throw new ValidationException("Price and currency must not be null");
        }
        if (this.value.compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidationException("Price must be greater or equal to zero");
        }
    }

    private void throwIfCurrenciesDoNotMatch(Price price) throws ValidationException {
        if (!price.getCurrency().equals(currency)) {
            throw new ValidationException("Currencies do not match");
        }
    }

    public Price add(Price price) throws ValidationException {
        price.validate();
        throwIfCurrenciesDoNotMatch(price);
        return new Price(value.add(price.value), currency);
    }

    public Price subtract(Price price) throws ValidationException {
        price.validate();
        throwIfCurrenciesDoNotMatch(price);
        return new Price(value.subtract(price.value), currency);
    }
}
