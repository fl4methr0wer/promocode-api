package pl.lodz.sii.promocodeapi;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.lodz.sii.promocodeapi.core.exception.ValidationException;
import pl.lodz.sii.promocodeapi.core.model.Price;
import java.math.BigDecimal;
import pl.lodz.sii.promocodeapi.core.model.Currency;

class PriceTest {

    @Test
    @DisplayName("Negative numbers are not acceptable")
    void testPriceDoesNotAcceptNegativeValues() {
        assertThrows(ValidationException.class,
                () -> new Price(new BigDecimal("-1"), Currency.EUR).validate(),
                "Price does not accept negative values");
    }

    @Test
    @DisplayName("Accepts zero value")
    void testPriceAcceptsZerValue() {
        assertDoesNotThrow(
                () -> new Price(new BigDecimal("0"), Currency.EUR).validate(),
                "Price should accept ZerValue");
    }

    @Test
    @DisplayName("Addition of same currencies works correctly")
    void testAdditionWorks() {
        Price price1, price2, result, expected;
        price1 = new Price(new BigDecimal("0.5"), Currency.USD);
        price2 = new Price(new BigDecimal("0.49"), Currency.USD);
        expected = new Price(new BigDecimal("0.99"), Currency.USD);
        result = price1.add(price2);
        assertEquals(result, expected);
    }

    @Test
    @DisplayName("Negative subtraction result is not accepted")
    void negativeResultIsImpossible() {
        Price price1, price2;
        price1 = new Price(BigDecimal.ZERO, Currency.USD);
        price2 = new Price(BigDecimal.ONE, Currency.USD);
        assertThrows(ValidationException.class,
                () -> price1.subtract(price2).validate(),
                "Negative price result of an operation is unacceptable");
    }

    @Test
    @DisplayName("Non matching currncies raise an exception")
    void differentCurrenciesRaiseAnException() {
        Price price1 = new Price(BigDecimal.ONE, Currency.EUR);
        Price price2 = new Price(BigDecimal.ONE, Currency.USD);
        assertThrows(ValidationException.class,
                () -> price1.subtract(price2),
                "Different currencies should raise an exception");

    }
}
