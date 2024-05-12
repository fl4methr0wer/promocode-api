package pl.lodz.sii.promocodeapi.core.model;

import java.math.BigDecimal;

public record SalesReportRecord(
        Currency currency,
        BigDecimal totalAmount,
        BigDecimal totalDiscount,
        Integer numberOfPurchases
) {}
