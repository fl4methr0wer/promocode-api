package pl.lodz.sii.promocodeapi;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.hibernate.procedure.ProcedureOutputs;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.lodz.sii.promocodeapi.core.exception.ValidationException;
import pl.lodz.sii.promocodeapi.core.model.Currency;
import pl.lodz.sii.promocodeapi.core.model.Price;
import pl.lodz.sii.promocodeapi.core.model.PromoCode;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

class PromoCodeTest {

    private PromoCode createValidPromoCode() {
        String code = "123";
        LocalDate date = LocalDate.now().plusDays(1);
        Price discount = new Price(BigDecimal.ONE, Currency.USD);
        Integer maximumUsages = 5;
        Integer hasBeenUsedTimes = 0;
        return new PromoCode(code, date, discount, maximumUsages, hasBeenUsedTimes);
    }

    @Test
    @DisplayName("Empty code is not accepted")
    void emptyPromoCodeIsInvalid() {
        PromoCode promoCode = createValidPromoCode();
        promoCode.setCode("");
        assertThrows(ValidationException.class, promoCode::validate);
    }

    @Test
    @DisplayName("PromoCode with whitespace characters is invalid")
    void codeWithSpacesIsInvalid() {
        PromoCode promoCode = createValidPromoCode();
        promoCode.setCode("10 d0ll@r$");
        assertThrows(ValidationException.class, promoCode::validate);
    }

    @Test
    @DisplayName("Short code is not accepted")
    void shortPromoCodeIsInvalid() {
        PromoCode promoCode = createValidPromoCode();
        promoCode.setCode("12");
        assertThrows(ValidationException.class, promoCode::validate);
    }

    @Test
    @DisplayName("Long name is not accepted")
    void longPromoCodeIsInvalid() {
        PromoCode promoCode = createValidPromoCode();
        promoCode.setCode("1234567890123456789012345");
        assertThrows(ValidationException.class, promoCode::validate);
    }

    @Test
    @DisplayName("Old promocode is not valid")
    void oldPromoCodeIsInvalid() {
        PromoCode promoCode = createValidPromoCode();
        promoCode.setExpires(LocalDate.now().minusDays(1));
        assertThrows(ValidationException.class, promoCode::validate);
    }

    @Test
    @DisplayName("Code has been used more than intended is not allowed")
    void codeHasBeenUsedMoreThanIntended() {
        PromoCode promoCode = createValidPromoCode();
        promoCode.setMaximumUsages(1);
        promoCode.setHasBeenUsedTimes(2);
        assertThrows(ValidationException.class, promoCode::validate);
    }

}
