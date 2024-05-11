package pl.lodz.sii.promocodeapi.persistence.mapper;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pl.lodz.sii.promocodeapi.core.exception.ValidationException;
import pl.lodz.sii.promocodeapi.core.model.Price;
import pl.lodz.sii.promocodeapi.core.model.PromoCode;
import pl.lodz.sii.promocodeapi.core.model.Purchase;
import pl.lodz.sii.promocodeapi.persistence.entity.PromoCodeEntity;
import pl.lodz.sii.promocodeapi.persistence.entity.PurchaseEntity;

import java.util.Optional;

@Component
@AllArgsConstructor
public class PurchaseOptionalMapper {

    private final ProductMapper productMapper;
    private final PromoCodeMapper promoCodeMapper;
    private final Logger LOG = LoggerFactory.getLogger(PurchaseOptionalMapper.class);

    public Optional<PurchaseEntity> toEntity(Purchase model) {
        PurchaseEntity entity = new PurchaseEntity();
        entity.setPurchaseDate(model.getDate());
        entity.setProduct(productMapper.toEntity(model.getProduct()));

        Optional<PromoCode> promoCode = model.getPromoCode();
        entity.getPromoCode() = promoCode.isPresent() ? promoCodeMapper.toEntity(promoCode.get()) : null;
        entity.setRegularPrice(model.getRegularPrice().getValue());
        entity.setCurrency(model.getRegularPrice().getCurrency());
        entity.setTotalPrice(model.getRegularPrice().getValue());
        return Optional.of(entity);
    }

    public Optional<Purchase> toModel(Optional<PurchaseEntity> optionalEntity) {
        if (optionalEntity.isEmpty()) {
            return Optional.empty();
        }
        Purchase model = new Purchase();
        PurchaseEntity entity = optionalEntity.get();
        model.setId(entity.getId());
        model.setDate(entity.getPurchaseDate());
        model.setProduct(productMapper.toModel(entity.getProduct()));
        PromoCodeEntity promoCodeEntity = optionalEntity.get().getPromoCode();
        model.getPromoCode() = (promoCodeEntity != null) ?
                Optional.ofNullable(promoCodeMapper.toModel(promoCodeEntity))
                : Optional.empty();
        Price regularPrice = new Price(entity.getRegularPrice(), entity.getCurrency());
        Price totalPrice = new Price(entity.getTotalPrice(), entity.getCurrency());
        model.setRegularPrice(regularPrice);
        model.setDiscountPrice(totalPrice);
        try {
            model.validate();
            return Optional.of(model);
        } catch (ValidationException e) {
            LOG.error(e.getMessage());
            return Optional.empty();
        }

    }
}
