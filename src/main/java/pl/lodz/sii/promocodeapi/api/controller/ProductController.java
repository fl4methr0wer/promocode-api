package pl.lodz.sii.promocodeapi.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lodz.sii.promocodeapi.api.mapper.ProductModelResponseMapper;
import pl.lodz.sii.promocodeapi.api.mapper.ProductRequestModelMapper;
import pl.lodz.sii.promocodeapi.api.model.product.ProductCreationRequest;
import pl.lodz.sii.promocodeapi.api.model.product.ProductResponse;
import pl.lodz.sii.promocodeapi.core.exception.ValidationException;
import pl.lodz.sii.promocodeapi.core.model.Product;
import pl.lodz.sii.promocodeapi.core.service.ProductService;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductRequestModelMapper requestModelMapper;
    private final ProductModelResponseMapper modelResponseMapper;

    @PostMapping
    ResponseEntity<Long> createProduct(@RequestBody ProductCreationRequest request) {
        Product product;
        try {
           product =  requestModelMapper.map(request);
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(productService.save(product).get());
    }

    @GetMapping
    ResponseEntity<List<ProductResponse>> findAllProducts() {
        List<Product> allProducts = productService.readAll();
        if (allProducts.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<ProductResponse> productResponses = allProducts.stream()
                .map(modelResponseMapper::map)
                .toList();
        return ResponseEntity.ok(productResponses);
    }

}
