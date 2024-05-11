package pl.lodz.sii.promocodeapi.core.service;

import pl.lodz.sii.promocodeapi.core.model.PromoCode;
import java.util.List;
import java.util.Optional;

public interface PromoCodeService {
    List<PromoCode> readAll();
    Optional<PromoCode> read(String code);
    Optional<String> save(PromoCode code);
    boolean update(PromoCode code);
    boolean deleteByCode(String code);
}
