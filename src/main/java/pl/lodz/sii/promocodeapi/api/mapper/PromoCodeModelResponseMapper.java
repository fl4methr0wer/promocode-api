package pl.lodz.sii.promocodeapi.api.mapper;

import org.springframework.stereotype.Component;
import pl.lodz.sii.promocodeapi.api.model.promocode.PromoCodeResponse;
import pl.lodz.sii.promocodeapi.core.exception.ValidationException;
import pl.lodz.sii.promocodeapi.core.model.Currency;
import pl.lodz.sii.promocodeapi.core.model.PromoCode;
import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class PromoCodeModelResponseMapper implements Mapper<PromoCode, PromoCodeResponse> {

    @Override
    public PromoCodeResponse map(PromoCode promoCode) throws ValidationException {
        String code = promoCode.getCode();
        LocalDate expires = promoCode.getExpires();
        BigDecimal amount = promoCode.getDiscount().getValue();
        Currency currency = promoCode.getDiscount().getCurrency();

        return new PromoCodeResponse(code, expires, amount, currency);
    }
}
