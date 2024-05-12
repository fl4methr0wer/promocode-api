package pl.lodz.sii.promocodeapi.api.model.product;

import pl.lodz.sii.promocodeapi.core.model.Currency;
import java.math.BigDecimal;

public record ProductCreationRequest(
        String name,
        String description,
        BigDecimal price,
        Currency currency
) {
   public ProductCreationRequest(String name, BigDecimal price, Currency currency) {
       this(
           name,
   "",
           price,
           currency);
   }
}
