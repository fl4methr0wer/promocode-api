package pl.lodz.sii.promocodeapi.core.repository;

import pl.lodz.sii.promocodeapi.core.model.PromoCode;
import java.util.List;
import java.util.Optional;

public interface PromoCodeRepo {
    Optional<PromoCode> findByCode(String code);
    List<PromoCode> readAll();
    Optional<String> save(PromoCode promoCode);
    boolean deleteByCode(String code);
    boolean update(PromoCode promoCode);
}
