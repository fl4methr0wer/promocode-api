package pl.lodz.sii.promocodeapi.api.mapper;

import org.springframework.stereotype.Component;
import pl.lodz.sii.promocodeapi.api.model.promocode.PromoCodeRequest;
import pl.lodz.sii.promocodeapi.core.exception.ValidationException;
import pl.lodz.sii.promocodeapi.core.model.Price;
import pl.lodz.sii.promocodeapi.core.model.PromoCode;

@Component
public class PromoCodeRequestModelMapper implements Mapper<PromoCodeRequest, PromoCode> {

    @Override
    public PromoCode map(PromoCodeRequest request) throws ValidationException {
        PromoCode code = new PromoCode();
        code.setCode(request.code());
        code.setExpires(request.expirationDate());
        code.setDiscount(new Price(request.amount(), request.currency()));
        if (request.maximumUses() != null) code.setMaximumUsages(request.maximumUses());
        code.validate();
        return code;
    }
}
