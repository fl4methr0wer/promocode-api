package pl.lodz.sii.promocodeapi.api.model.promocode;

import pl.lodz.sii.promocodeapi.core.model.Currency;
import java.math.BigDecimal;
import java.time.LocalDate;

public record PromoCodeResponse(
        String code,
        LocalDate expirationDate,
        BigDecimal amount,
        Currency currency
) {}
