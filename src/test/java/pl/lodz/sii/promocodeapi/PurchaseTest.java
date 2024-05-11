package pl.lodz.sii.promocodeapi;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.lodz.sii.promocodeapi.core.model.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PurchaseTest {

    private Product create100USDProduct() {
        Long id = 1L;
        String Name = "test product";
        Price price = new Price(new BigDecimal("100.00"), Currency.USD);
        return new Product(id, Name, price);
    }

    private PromoCode createValid10USDPromoCode() {
        return new PromoCode("qwerty",
                LocalDate.now().plusDays(1),
                new Price(new BigDecimal("10"), Currency.USD),
                1,
                1);
    }

    @Test
    @DisplayName("PurchaseWithout a promocode has the same regular and discounted price")
    void purchaseWithoutAPromocode() {
        Product product100USD = create100USDProduct();

        Purcase purcase = new Purcase(product100USD);

        assertEquals(purcase.getDiscountPrice(), purcase.getDiscountPrice());
    }

    @Test
    @DisplayName("Promo code with the same currency works")
    void promocodeWithSameCurrency() {
        Product product100USD = create100USDProduct();
        PromoCode valid10USDPromoCode = createValid10USDPromoCode();
        Purcase purcase = new Purcase(product100USD, valid10USDPromoCode);
        assertEquals(purcase.getDiscountPrice(), new Price(new BigDecimal("90"), Currency.USD));
    }

    @Test
    @DisplayName("Promo code with wrong currency does not apply. Total price is normal price")
    void promocodeWithWrongCurrency() {
        Product product100EUR = create100USDProduct();
        product100EUR.setPrice(new Price(new BigDecimal("100"), Currency.EUR));
        PromoCode valid10USDPromoCode = createValid10USDPromoCode();
        Purcase purcase = new Purcase(product100EUR, valid10USDPromoCode);

        assertEquals(purcase.getDiscountPrice(), purcase.getRegularPrice());
    }

    @Test
    @DisplayName("Purchase with null promo code happens like a purchase witout any promo")
    void purchaseWithNullPromoCode() {
        Product product100USD = create100USDProduct();
        PromoCode nullPromoCode = null;
        Purcase purchase = new Purcase(product100USD, nullPromoCode);

        assertEquals(purchase.getDiscountPrice(), product100USD.getPrice());
    }
}
