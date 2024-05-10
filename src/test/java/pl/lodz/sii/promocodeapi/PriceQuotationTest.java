package pl.lodz.sii.promocodeapi;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.lodz.sii.promocodeapi.core.model.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PriceQuotationTest {

    private Product create100USDProduct() {
        Long id = 1L;
        String Name = "test product";
        Price price = new Price(new BigDecimal("100.00"), Currency.USD);
        return new Product(id, Name, price);
    }

    @Test
    @DisplayName("Price quotation without a promo has the same price")
    void priceQuotationWithoutAPromoHasTheSamePrice() {
        Product product = create100USDProduct();
        PriceQuotation quotation = new PriceQuotation(product);

        assertEquals(quotation.getPrice(), new Price(new BigDecimal("100"), Currency.USD));
    }

    @Test
    @DisplayName("10 Euro promo code does not apply 100 USD Product with warning")
    void productPriceAndPromoCodeValueDoNotMatch() {
        Product product = create100USDProduct();
        PromoCode code = new PromoCode("testPromoo",
                LocalDate.now().plusDays(1),
                new Price(new BigDecimal("10"), Currency.EUR),
                10,
                1);

        PriceQuotation quotation = new PriceQuotation(product, code);

        assertNotEquals(quotation.getWarning(), null);
        assertEquals(product.getPrice(), quotation.getPrice());
    }

    @Test
    @DisplayName("10 USD promo code applies to 100 USD Product")
    void productPriceAndPromoCodeAppliesTo100USDProduct() {
        Product product = create100USDProduct();
        PromoCode promoCode = new PromoCode("testPromo",
                LocalDate.now().plusDays(1),
                new Price(new BigDecimal("10"), Currency.USD),
                10,
                1);
        PriceQuotation quotation = new PriceQuotation(product, promoCode);

        assertEquals(quotation.getWarning(), "");
        assertEquals(quotation.getPrice(), new Price(new BigDecimal("90"), Currency.USD));
    }

    @Test
    @DisplayName("Promo code with value exceeding the product price results in 0 price")
    void productPriceAndPromoCodeExceedsTheProductPriceResultsIn0Price() {
        Product product = create100USDProduct();
        PromoCode promoCode = new PromoCode("testPromoCode",
                LocalDate.now().plusDays(1),
                new Price(new BigDecimal("999"), Currency.USD),
                10,
                1);

        PriceQuotation quotation = new PriceQuotation(product, promoCode);

        assertEquals(quotation.getWarning(), "");
        assertEquals(quotation.getPrice(), new Price(BigDecimal.ZERO, Currency.USD));
    }

}
