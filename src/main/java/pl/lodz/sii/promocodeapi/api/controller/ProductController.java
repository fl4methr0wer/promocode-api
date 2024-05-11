package pl.lodz.sii.promocodeapi.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.lodz.sii.promocodeapi.api.mapper.ProductRequestModelMapper;
import pl.lodz.sii.promocodeapi.api.model.product.ProductCreationRequest;
import pl.lodz.sii.promocodeapi.core.exception.ValidationException;
import pl.lodz.sii.promocodeapi.core.model.Product;
import pl.lodz.sii.promocodeapi.core.service.ProductService;

@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductRequestModelMapper requestModelMapper;

    @PostMapping
    ResponseEntity<Long> createProduct(@RequestBody ProductCreationRequest request) {
        Product product;
        try {
           product =  requestModelMapper.map(request);
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().build();
        }
        System.out.println(product);
        return ResponseEntity.ok(product.getId());
    }

}
