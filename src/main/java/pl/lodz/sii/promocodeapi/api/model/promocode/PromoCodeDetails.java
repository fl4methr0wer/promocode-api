package pl.lodz.sii.promocodeapi.api.model.promocode;

public record PromoCodeDetails(
        PromoCodeResponse promoCode,
        Integer timesHasBeenUsed
) {}
