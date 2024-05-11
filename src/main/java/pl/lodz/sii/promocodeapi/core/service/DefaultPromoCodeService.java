package pl.lodz.sii.promocodeapi.core.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.lodz.sii.promocodeapi.core.model.PromoCode;
import pl.lodz.sii.promocodeapi.core.repository.PromoCodeRepo;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DefaultPromoCodeService implements PromoCodeService {

    PromoCodeRepo repo;

    @Override
    public List<PromoCode> readAll() {
        return repo.readAll();
    }

    @Override
    public Optional<PromoCode> read(String code) {
        return repo.findByCode(code);
    }

    @Override
    public Optional<String> save(PromoCode code) {
        return repo.save(code);
    }

    @Override
    public boolean update(PromoCode code) {
        return repo.update(code);
    }

    @Override
    public boolean deleteByCode(String code) {
        return repo.deleteByCode(code);
    }
}
