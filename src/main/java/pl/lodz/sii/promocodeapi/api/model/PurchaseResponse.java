package pl.lodz.sii.promocodeapi.api.model;

import pl.lodz.sii.promocodeapi.api.model.product.ProductResponse;
import pl.lodz.sii.promocodeapi.core.model.Price;
import java.time.LocalDate;

public record PurchaseResponse(
        Long id,
        LocalDate purchaseDate,
        ProductResponse product,
        String promoCode,
        Price regularPrice,
        Price totalPrice
) {}
