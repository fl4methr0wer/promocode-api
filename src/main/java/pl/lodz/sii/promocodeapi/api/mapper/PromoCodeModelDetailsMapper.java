package pl.lodz.sii.promocodeapi.api.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.lodz.sii.promocodeapi.api.model.promocode.PromoCodeDetails;
import pl.lodz.sii.promocodeapi.core.exception.ValidationException;
import pl.lodz.sii.promocodeapi.core.model.PromoCode;

@Component
@AllArgsConstructor
public class PromoCodeModelDetailsMapper implements Mapper<PromoCode, PromoCodeDetails> {

    private final PromoCodeModelResponseMapper responseMapper;

    @Override
    public PromoCodeDetails map(PromoCode model) throws ValidationException {
        return new PromoCodeDetails(responseMapper.map(model), model.getHasBeenUsedTimes());
    }
}
