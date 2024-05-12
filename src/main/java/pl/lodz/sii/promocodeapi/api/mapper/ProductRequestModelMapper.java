package pl.lodz.sii.promocodeapi.api.mapper;

import org.springframework.stereotype.Component;
import pl.lodz.sii.promocodeapi.api.model.product.ProductCreationRequest;
import pl.lodz.sii.promocodeapi.core.exception.ValidationException;
import pl.lodz.sii.promocodeapi.core.model.Price;
import pl.lodz.sii.promocodeapi.core.model.Product;

@Component
public class ProductRequestModelMapper implements Mapper<ProductCreationRequest, Product> {

    @Override
    public Product map(ProductCreationRequest request) throws ValidationException {
        Product product = new Product();
        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(new Price(request.price(), request.currency()));
        product.validate();
        return product;
    }
}
