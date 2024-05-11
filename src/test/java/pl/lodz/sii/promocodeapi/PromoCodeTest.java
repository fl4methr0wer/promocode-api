package pl.lodz.sii.promocodeapi;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.lodz.sii.promocodeapi.core.exception.PromoCodeException;
import pl.lodz.sii.promocodeapi.core.exception.ValidationException;
import pl.lodz.sii.promocodeapi.core.model.Currency;
import pl.lodz.sii.promocodeapi.core.model.Price;
import pl.lodz.sii.promocodeapi.core.model.Product;
import pl.lodz.sii.promocodeapi.core.model.PromoCode;
import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

class PromoCodeTest {

    private PromoCode createValid1USDPromoCode() {
        String code = "123";
        LocalDate date = LocalDate.now().plusDays(1);
        Price discount = new Price(BigDecimal.ONE, Currency.USD);
        Integer maximumUsages = 5;
        Integer hasBeenUsedTimes = 0;
        return new PromoCode(code, date, discount, maximumUsages, hasBeenUsedTimes);
    }

    private Product create10USDProduct() {
        Product product = new Product();
        product.setId(1L);
        product.setName("testProduct");
        product.setPrice(new Price(new BigDecimal("10"), Currency.USD));
        return product;
    }

    @Test
    @DisplayName("Empty code is not accepted")
    void emptyPromoCodeIsInvalid() {
        PromoCode promoCode = createValid1USDPromoCode();
        promoCode.setCode("");
        assertThrows(ValidationException.class, promoCode::validate);
    }

    @Test
    @DisplayName("PromoCode with whitespace characters is invalid")
    void codeWithSpacesIsInvalid() {
        PromoCode promoCode = createValid1USDPromoCode();
        promoCode.setCode("10 d0ll@r$");
        assertThrows(ValidationException.class, promoCode::validate);
    }

    @Test
    @DisplayName("Short code is not accepted")
    void shortPromoCodeIsInvalid() {
        PromoCode promoCode = createValid1USDPromoCode();
        promoCode.setCode("12");
        assertThrows(ValidationException.class, promoCode::validate);
    }

    @Test
    @DisplayName("Long name is not accepted")
    void longPromoCodeIsInvalid() {
        PromoCode promoCode = createValid1USDPromoCode();
        promoCode.setCode("1234567890123456789012345");
        assertThrows(ValidationException.class, promoCode::validate);
    }

    @Test
    @DisplayName("Usage of old promocode is not allowed")
    void oldPromoCodeIsInvalid() {
        PromoCode promoCode = createValid1USDPromoCode();
        promoCode.setExpires(LocalDate.now().minusDays(1));
        Product product = create10USDProduct();
        assertThrows(
                PromoCodeException.class,
                () -> promoCode.applyTo(product)
        );
    }

    @Test
    @DisplayName("Usage of promocoode with exceeded usagee limit is prohibited")
    void codeHasBeenUsedMoreThanIntended() {
        PromoCode promoCode = createValid1USDPromoCode();
        promoCode.setMaximumUsages(1);
        promoCode.setHasBeenUsedTimes(2);
        Product testProduct = create10USDProduct();
        assertThrows(PromoCodeException.class,
                () -> promoCode.applyTo(testProduct));
    }

}
