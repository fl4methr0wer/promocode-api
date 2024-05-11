package pl.lodz.sii.promocodeapi.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.lodz.sii.promocodeapi.api.model.PriceQuotationResponse;
import pl.lodz.sii.promocodeapi.core.model.PriceQuotation;

@RestController
@RequestMapping("/api/purchases")
@AllArgsConstructor
public class SalesController {

    private final SalesService service;

    @GetMapping("/quotation")
    ResponseEntity<PriceQuotationResponse> calculateDiscountedPrice(@RequestParam Long productId,
                                                                    @RequestParam String promocode) {
        PriceQuotation quotation;
        try {
            quotation = salesService.calculateDiscount(productId, promocode);
        } catch (PromoCodeException | ObjectNotFoundException e) {
            LOG.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
        PriceQuotationResponse response = priceQuotationResponseMapper.map(quotation);
        return ResponseEntity.ok(response);
    }
}
