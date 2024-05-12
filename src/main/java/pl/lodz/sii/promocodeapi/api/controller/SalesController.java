package pl.lodz.sii.promocodeapi.api.controller;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lodz.sii.promocodeapi.api.mapper.PriceQuotationModelResponseMapper;
import pl.lodz.sii.promocodeapi.api.mapper.PurchaseModelResponseMapper;
import pl.lodz.sii.promocodeapi.api.model.PriceQuotationResponse;
import pl.lodz.sii.promocodeapi.api.model.PurchaseResponse;
import pl.lodz.sii.promocodeapi.api.model.product.ProductPromoRequest;
import pl.lodz.sii.promocodeapi.core.model.PriceQuotation;
import pl.lodz.sii.promocodeapi.core.model.Purchase;
import pl.lodz.sii.promocodeapi.core.model.SalesReportRecord;
import pl.lodz.sii.promocodeapi.core.service.SalesService;
import pl.lodz.sii.promocodeapi.core.exception.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/purchases")
@AllArgsConstructor
public class SalesController {

    private final SalesService salesService;
    private final PriceQuotationModelResponseMapper priceQuotationResponseMapper;
    private final PurchaseModelResponseMapper purchaseResponseMapper;

    private final Logger LOG = LoggerFactory.getLogger(SalesController.class);

    @GetMapping("/quotation")
    ResponseEntity<PriceQuotationResponse> calculateDiscountedPrice(@RequestBody
                                                                    ProductPromoRequest request) {
        PriceQuotation quotation;
        try {
            quotation = salesService.calculateDiscount(request.productId(), request.promoCode());
        } catch (PromoCodeException | ObjectNotFoundException e) {
            LOG.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
        PriceQuotationResponse response = priceQuotationResponseMapper.map(quotation);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<PurchaseResponse> purchase(@RequestBody
                                                     ProductPromoRequest request) {
        Long productId = request.productId();
        String promocode = request.promoCode();
        Optional<Purchase> purchase;
        try {
            purchase = (promocode != null) ?
                    salesService.makePurchaseWithPromoCode(productId, promocode)
                    : salesService.makePurchase(productId);
        } catch (ObjectNotFoundException | PromoCodeException e) {
            LOG.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
        return purchase.isPresent() ?
                ResponseEntity.ok(purchaseResponseMapper.map(purchase.get()))
                : ResponseEntity.internalServerError().build();
    }

    @GetMapping
    ResponseEntity<List<PurchaseResponse>> getAllPurchases() {
        List<PurchaseResponse> purchases = salesService.getAllPurchases().stream()
                .map(purchaseResponseMapper::map)
                .toList();
        return purchases.isEmpty() ?
                ResponseEntity.notFound().build()
                : ResponseEntity.ok(purchases);
    }

    @GetMapping("/{id}")
    ResponseEntity<PurchaseResponse> getPurchaseById(@PathVariable Long id) {
        Optional<Purchase> purchase = salesService.getById(id);
        PurchaseResponse response;
        if (purchase.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        try {
            response = purchaseResponseMapper.map(purchase.get());
        } catch (Exception e) {
            LOG.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/report")
    ResponseEntity<List<SalesReportRecord>> getSalesReport() {
        List<SalesReportRecord> salesReportRecords = salesService.getSalesReport();
        return salesReportRecords.isEmpty() ?
                ResponseEntity.ok(salesReportRecords)
                : ResponseEntity.notFound().build();
    }
}
