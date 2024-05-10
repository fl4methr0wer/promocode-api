package pl.lodz.sii.promocodeapi.core.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import pl.lodz.sii.promocodeapi.core.exception.ValidationException;

import java.math.BigDecimal;

@Getter
@ToString
@EqualsAndHashCode
public class Price {
    private BigDecimal value;
    private Currency currency;

    public Price(BigDecimal value, Currency currency) throws ValidationException {
        this.value = value;
        this.currency = currency;
        validatePrice(this);
    }

    public Price add(Price price) throws ValidationException {
        validatePrice(price);
        return new Price(value.add(price.value), currency);
    }
    public Price subtract(Price price) throws ValidationException {
        validatePrice(price);
        return new Price(value.subtract(price.value), currency);
    }
    private void validatePrice(Price price) throws ValidationException {
        if (price.value == null || price.currency == null) {
            throw new ValidationException("Price and currency must not be null");
        }
        if (price.getValue().compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidationException("Price must be greater or equal to zero");
        }
        if (!price.getCurrency().equals(currency)) {
            throw new ValidationException("Currencies do not match");
        }
    }
}
