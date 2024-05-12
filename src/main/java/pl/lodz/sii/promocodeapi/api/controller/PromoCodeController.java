package pl.lodz.sii.promocodeapi.api.controller;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lodz.sii.promocodeapi.api.mapper.PromoCodeModelDetailsMapper;
import pl.lodz.sii.promocodeapi.api.mapper.PromoCodeModelResponseMapper;
import pl.lodz.sii.promocodeapi.api.mapper.PromoCodeRequestModelMapper;
import pl.lodz.sii.promocodeapi.api.model.promocode.PromoCodeDetails;
import pl.lodz.sii.promocodeapi.api.model.promocode.PromoCodeRequest;
import pl.lodz.sii.promocodeapi.api.model.promocode.PromoCodeResponse;
import pl.lodz.sii.promocodeapi.core.exception.ValidationException;
import pl.lodz.sii.promocodeapi.core.model.PromoCode;
import pl.lodz.sii.promocodeapi.core.service.PromoCodeService;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api/promo")
@AllArgsConstructor
public class PromoCodeController {

    private final PromoCodeService promoCodeService;
    private final PromoCodeRequestModelMapper requestPromoCodeMapper;
    private final PromoCodeModelResponseMapper modelResponseMapper;
    private final PromoCodeModelDetailsMapper modelDetailsMapper;
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

    @GetMapping
    ResponseEntity<List<PromoCodeResponse>> getAllPromoCodes() {
        List<PromoCodeResponse> promoCodeResponses = promoCodeService.readAll().stream()
                .map(modelResponseMapper::map)
                .toList();
        return promoCodeResponses.isEmpty() ?
                ResponseEntity.notFound().build()
                : ResponseEntity.ok(promoCodeResponses);
    }

    @GetMapping("/{code}")
    ResponseEntity<PromoCodeResponse> getPromoCode(@PathVariable String code) {
        Optional<PromoCode> promoCode = promoCodeService.read(code);
        return promoCode.isPresent() ?
                ResponseEntity.ok(modelResponseMapper.map(promoCode.get()))
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/details")
    ResponseEntity<List<PromoCodeDetails>> getAllPomoCodeDetails() {
        List<PromoCode> promoCodes = promoCodeService.readAll();
        if (promoCodes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<PromoCodeDetails> promoCodeDetailsList = promoCodes.stream()
                .map(modelDetailsMapper::map)
                .toList();
        return ResponseEntity.ok(promoCodeDetailsList);
    }

    @GetMapping("/{code}/details")
    ResponseEntity<PromoCodeDetails> getPromoCodeDetails(@PathVariable("code") String code) {
        Optional<PromoCode> promoCode = promoCodeService.read(code);
        return promoCode.isPresent() ?
                ResponseEntity.ok(modelDetailsMapper.map(promoCode.get()))
                : ResponseEntity.notFound().build();
    }

}
