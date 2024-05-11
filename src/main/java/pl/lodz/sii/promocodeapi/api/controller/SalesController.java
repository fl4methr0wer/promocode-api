package pl.lodz.sii.promocodeapi.api.controller;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.lodz.sii.promocodeapi.api.mapper.PriceQuotationModelResponseMapper;
import pl.lodz.sii.promocodeapi.api.model.PriceQuotationResponse;
import pl.lodz.sii.promocodeapi.core.model.PriceQuotation;
import pl.lodz.sii.promocodeapi.core.service.SalesService;
import pl.lodz.sii.promocodeapi.core.exception.*;

@RestController
@RequestMapping("/api/purchases")
@AllArgsConstructor
public class SalesController {

    private final SalesService salesService;
    private final PriceQuotationModelResponseMapper priceQuotationResponseMapper;

    private final Logger LOG = LoggerFactory.getLogger(SalesController.class);

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
