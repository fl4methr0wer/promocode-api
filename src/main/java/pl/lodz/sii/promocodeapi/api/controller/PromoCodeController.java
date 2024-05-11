package pl.lodz.sii.promocodeapi.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.lodz.sii.promocodeapi.api.mapper.PromoCodeRequestModelMapper;
import pl.lodz.sii.promocodeapi.api.model.promocode.PromoCodeRequest;
import pl.lodz.sii.promocodeapi.core.exception.ValidationException;
import pl.lodz.sii.promocodeapi.core.model.PromoCode;

import java.util.Optional;

@RestController
@RequestMapping("/api/promo")
@AllArgsConstructor
public class PromoCodeController {

    private final PromoCodeRequestModelMapper requestPromoCodeMapper;

    @PostMapping
    ResponseEntity<String> createPromoCode(@RequestBody PromoCodeRequest promoCodeRequest) {
        try {
            PromoCode code = requestPromoCodeMapper.map(promoCodeRequest); // should throw an exception on wrong payload
            System.out.println("CONTROLLER: " + code);
            return ResponseEntity.ok(code.getCode());
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body("Wrong payload");
        }
    }
}
