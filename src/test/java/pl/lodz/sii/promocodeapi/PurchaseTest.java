package pl.lodz.sii.promocodeapi;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.lodz.sii.promocodeapi.core.model.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PurchaseTest {

    private Product create100USDProduct() {
        Long id = 1L;
        String Name = "test product";
        String description = "test product";
        Price price = new Price(new BigDecimal("100.00"), Currency.USD);
        return new Product(id, Name, description, price);
    }

    private PromoCode createValid10USDPromoCode() {
        return new PromoCode("qwerty",
                LocalDate.now().plusDays(1),
                new Price(new BigDecimal("10"), Currency.USD),
                1,
                0);
    }

    @Test
    @DisplayName("PurchaseWithout a promocode has the same regular and discounted price")
    void purchaseWithoutAPromocode() {
        Product product100USD = create100USDProduct();

        Purchase purchase = new Purchase(product100USD);

        Price expected = purchase.getRegularPrice();
        Price actual = purchase.getTotalPrice();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Promo code with the same currency works")
    void promocodeWithSameCurrency() {
        Product product100USD = create100USDProduct();
        PromoCode valid10USDPromoCode = createValid10USDPromoCode();
        Purchase purchase = new Purchase(product100USD, valid10USDPromoCode);

        Price actual = purchase.getTotalPrice();

        Price expected = new Price(new BigDecimal("90"), Currency.USD);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Promo code with wrong currency does not apply. Total price is normal price")
    void promocodeWithWrongCurrency() {
        Product product100EUR = create100USDProduct();
        product100EUR.setPrice(new Price(new BigDecimal("100"), Currency.EUR));
        PromoCode valid10USDPromoCode = createValid10USDPromoCode();
        Purchase purchase = new Purchase(product100EUR, valid10USDPromoCode);

        assertEquals(purchase.getTotalPrice(), purchase.getRegularPrice());
    }

    @Test
    @DisplayName("Purchase with null promo code happens like a purchase witout any promo")
    void purchaseWithNullPromoCode() {
        Product product100USD = create100USDProduct();
        PromoCode nullPromoCode = null;
        Purchase purchase = new Purchase(product100USD, nullPromoCode);

        assertEquals(product100USD.getPrice() , purchase.getTotalPrice());
    }

    @Test
    @DisplayName("Procode with value exceeding the productValue results in 0 total price")
    void procodeWithValueExceeding0TotalPrice() {
        Product product5USD = create100USDProduct();
        product5USD.setPrice(new Price(new BigDecimal("5"), Currency.USD));
        PromoCode valid10USDPromoCode = createValid10USDPromoCode();

        Purchase purchase = new Purchase(product5USD, valid10USDPromoCode);
        Price expected = new Price(BigDecimal.ZERO, Currency.USD);

        assertEquals(expected, purchase.getTotalPrice());
    }
}
