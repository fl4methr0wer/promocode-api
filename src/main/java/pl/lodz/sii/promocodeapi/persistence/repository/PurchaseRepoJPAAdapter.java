package pl.lodz.sii.promocodeapi.persistence.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.sii.promocodeapi.core.model.PromoCode;
import pl.lodz.sii.promocodeapi.core.model.Purchase;
import pl.lodz.sii.promocodeapi.core.repository.PromoCodeRepo;
import pl.lodz.sii.promocodeapi.core.repository.PurchaseRepo;
import pl.lodz.sii.promocodeapi.persistence.entity.PurchaseEntity;
import pl.lodz.sii.promocodeapi.persistence.mapper.PurchaseOptionalMapper;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class PurchaseRepoJPAAdapter implements PurchaseRepo {

    private final PurchaseJPARepo purchaseRepo;
    private final PurchaseOptionalMapper optionalMapper;
    private final PromoCodeRepo promoCodeRepo;

    @Override
    public Optional<Purchase> findById(Long id) {
        return optionalMapper.toModel(purchaseRepo.findById(id));
    }

    @Override
    public List<Purchase> readAll() {
        List<PurchaseEntity> purchaseEntities = purchaseRepo.findAll();
        return purchaseEntities.stream()
                .map(Optional::of)
                .map(optionalMapper::toModel)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    @Transactional
    @Override
    public Optional<Purchase> save(Purchase purchase) {
        Optional<PurchaseEntity> entity = optionalMapper.toEntity(purchase);
        Optional<PromoCode> promo = purchase.getPromoCode();
        if (promo.isPresent()) {
            promoCodeRepo.incrementHasBeenUsed(promo.get());
        }
        return entity.isEmpty() ?
                Optional.empty()
                : optionalMapper.toModel(
                        Optional.of(
                                purchaseRepo.save(entity.get())
                        )
                );
    }
}
