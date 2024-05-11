package pl.lodz.sii.promocodeapi.api.model;

import pl.lodz.sii.promocodeapi.api.model.product.ProductResponse;
import pl.lodz.sii.promocodeapi.core.model.Currency;
import java.math.BigDecimal;

public record PriceQuotationResponse(
        ProductResponse product,
        BigDecimal totalPrice,
        Currency currency,
        String warning
) {}
