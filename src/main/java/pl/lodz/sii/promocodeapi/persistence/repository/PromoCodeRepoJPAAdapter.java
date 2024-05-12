package pl.lodz.sii.promocodeapi.persistence.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.sii.promocodeapi.core.model.PromoCode;
import pl.lodz.sii.promocodeapi.core.repository.PromoCodeRepo;
import pl.lodz.sii.promocodeapi.persistence.entity.PromoCodeEntity;
import pl.lodz.sii.promocodeapi.persistence.mapper.PromoCodeMapper;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class PromoCodeRepoJPAAdapter implements PromoCodeRepo {

    private final PromoCodeEntityJPARepo repo;
    private final PromoCodeMapper mapper;

    @Override
    public Optional<PromoCode> findByCode(String code) {
        Optional<PromoCodeEntity> foundPromo = repo.findById(code);
        return foundPromo.isPresent() ?
                Optional.of(mapper.toModel(foundPromo.get()))
                : Optional.empty();
    }

    @Override
    public List<PromoCode> readAll() {
        return repo.findAll().stream()
                .map(mapper::toModel)
                .toList();
    }

    @Override
    public Optional<String> save(PromoCode promoCode) {
        PromoCodeEntity saved = repo.save(mapper.toEntity(promoCode));
        return Optional.of(saved.getCode());
    }

    @Transactional
    @Override
    public boolean deleteByCode(String code) {
        Optional<PromoCodeEntity> entity = repo.findById(code);
        if (entity.isEmpty()) {
            return false;
        } else {
            repo.deleteById(code);
            return true; // has been deleted
        }
    }

    @Transactional
    @Override
    public boolean update(PromoCode promoCode) {
        Optional<PromoCodeEntity> entity = repo.findById(promoCode.getCode());
        if (entity.isEmpty()) {
            return false; // has not been updated
        } else {
            repo.save(mapper.toEntity(promoCode));
            return true; // has been updated
        }
    }

    @Transactional
    @Override
    public boolean incrementHasBeenUsed(PromoCode promoCode) {
        Optional<PromoCodeEntity> optionalPromoCode = repo.findById(promoCode.getCode());
        if (optionalPromoCode.isEmpty()) {
            return false;
        }
        Integer oldHasBeenUsedTimes = optionalPromoCode.get().getHasBeenUsedTimes();
        optionalPromoCode.get().setHasBeenUsedTimes(oldHasBeenUsedTimes + 1);
        repo.save(optionalPromoCode.get());
        return true;
    }
}
