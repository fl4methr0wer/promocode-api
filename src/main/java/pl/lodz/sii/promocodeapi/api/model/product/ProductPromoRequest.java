package pl.lodz.sii.promocodeapi.api.model.product;

public record ProductPromoRequest(
        Long productId,
        String promoCode
) {}
