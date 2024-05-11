package pl.lodz.sii.promocodeapi.api.mapper;

import org.springframework.stereotype.Component;
import pl.lodz.sii.promocodeapi.api.model.product.ProductResponse;
import pl.lodz.sii.promocodeapi.core.exception.ValidationException;
import pl.lodz.sii.promocodeapi.core.model.Currency;
import pl.lodz.sii.promocodeapi.core.model.Product;

import java.math.BigDecimal;

@Component
public class ProductModelResponseMapper implements Mapper<Product, ProductResponse> {

    @Override
    public ProductResponse map(Product product) throws ValidationException {
        Long id = product.getId();
        String name = product.getName();
        BigDecimal price = product.getPrice().getValue();
        Currency currency = product.getPrice().getCurrency();
        return new ProductResponse(id, name, price, currency);
    }
}
