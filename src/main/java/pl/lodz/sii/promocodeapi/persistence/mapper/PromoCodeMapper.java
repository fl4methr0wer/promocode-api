package pl.lodz.sii.promocodeapi.persistence.mapper;

import org.springframework.stereotype.Component;
import pl.lodz.sii.promocodeapi.core.exception.ValidationException;
import pl.lodz.sii.promocodeapi.core.model.Price;
import pl.lodz.sii.promocodeapi.core.model.PromoCode;
import pl.lodz.sii.promocodeapi.persistence.entity.PromoCodeEntity;

@Component
public class PromoCodeMapper implements ModelEntityMapper<PromoCode, PromoCodeEntity> {

    @Override
    public PromoCodeEntity toEntity(PromoCode model) {
        PromoCodeEntity entity = new PromoCodeEntity();
        entity.setCode(model.getCode());
        entity.setExpires(model.getExpires());
        entity.setDiscountValue(model.getDiscount().getValue());
        entity.setCurrency(model.getDiscount().getCurrency());
        entity.setMaximumUsages(model.getMaximumUsages());
        entity.setHasBeenUsedTimes(model.getHasBeenUsedTimes());
        return entity;
    }

    @Override
    public PromoCode toModel(PromoCodeEntity entity) throws ValidationException {
        PromoCode model = new PromoCode();
        model.setCode(entity.getCode());
        model.setExpires(entity.getExpires());
        model.setDiscount(new Price(entity.getDiscountValue(), entity.getCurrency()));
        model.setMaximumUsages(entity.getMaximumUsages());
        model.setHasBeenUsedTimes(entity.getHasBeenUsedTimes());

        model.validate();
        return model;
    }
}
