package pl.lodz.sii.promocodeapi.api.model.product;

import pl.lodz.sii.promocodeapi.core.model.Currency;
import java.math.BigDecimal;

public record ProductResponse(
        Long id,
        String name,
        String description,
        BigDecimal price,
        Currency currency
) {}