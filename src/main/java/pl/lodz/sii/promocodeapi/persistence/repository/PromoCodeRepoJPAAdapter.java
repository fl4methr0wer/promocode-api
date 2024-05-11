package pl.lodz.sii.promocodeapi.persistence.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.lodz.sii.promocodeapi.core.model.PromoCode;
import pl.lodz.sii.promocodeapi.core.repository.PromoCodeRepo;
import pl.lodz.sii.promocodeapi.persistence.entity.PromoCodeEntity;
import pl.lodz.sii.promocodeapi.persistence.mapper.ModelEntityMapper;
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
        return Optional.empty();
    }

    @Override
    public List<PromoCode> readAll() {
        return List.of();
    }

    @Override
    public Optional<String> save(PromoCode promoCode) {
        PromoCodeEntity saved = repo.save(mapper.toEntity(promoCode));
        return Optional.of(saved.getCode());
    }

    @Override
    public boolean deleteByCode(String code) {
        return false;
    }

    @Override
    public boolean update(PromoCode promoCode) {
        return false;
    }
}
