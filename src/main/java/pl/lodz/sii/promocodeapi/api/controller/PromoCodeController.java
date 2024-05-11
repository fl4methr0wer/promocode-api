package pl.lodz.sii.promocodeapi.api.controller;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.lodz.sii.promocodeapi.api.mapper.PromoCodeRequestModelMapper;
import pl.lodz.sii.promocodeapi.api.model.promocode.PromoCodeRequest;
import pl.lodz.sii.promocodeapi.core.exception.ValidationException;
import pl.lodz.sii.promocodeapi.core.model.PromoCode;
import pl.lodz.sii.promocodeapi.core.service.PromoCodeService;
import java.util.Optional;

@RestController
@RequestMapping("/api/promo")
@AllArgsConstructor
public class PromoCodeController {

    private final PromoCodeService promoCodeService;
    private final PromoCodeRequestModelMapper requestPromoCodeMapper;
    private final Logger LOG = LoggerFactory.getLogger(PromoCodeController.class);

    @PostMapping
    ResponseEntity<String> createPromoCode(@RequestBody PromoCodeRequest promoCodeRequest) {
        PromoCode code;
        try {
            code = requestPromoCodeMapper.map(promoCodeRequest); // should throw an exception on wrong payload
        } catch (ValidationException e) {
            LOG.error(e.getMessage());
            return ResponseEntity.badRequest().body("Wrong payload. " + e.getMessage());
        }
        Optional<String> savedCode = promoCodeService.save(code);
        return savedCode.isPresent() ?
                ResponseEntity.ok(savedCode.get())
                : ResponseEntity.internalServerError().body("Promo code creation failed");
    }
}
