package pl.lodz.sii.promocodeapi.persistence.repository;

import pl.lodz.sii.promocodeapi.core.model.PromoCode;
import pl.lodz.sii.promocodeapi.core.repository.PromoCodeRepo;
import java.util.List;
import java.util.Optional;

public class PromoCodeRepoJPAAdapter implements PromoCodeRepo {

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
        return Optional.empty();
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
