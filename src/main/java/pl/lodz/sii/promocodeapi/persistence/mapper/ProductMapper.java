package pl.lodz.sii.promocodeapi.persistence.mapper;

import org.springframework.stereotype.Component;
import pl.lodz.sii.promocodeapi.core.exception.ValidationException;
import pl.lodz.sii.promocodeapi.core.model.Currency;
import pl.lodz.sii.promocodeapi.core.model.Price;
import pl.lodz.sii.promocodeapi.core.model.Product;
import pl.lodz.sii.promocodeapi.persistence.entity.ProductEntity;
import java.math.BigDecimal;

@Component
public class ProductMapper implements ModelEntityMapper<Product, ProductEntity> {

    @Override
    public ProductEntity toEntity(Product product) {
        Long id = product.getId();
        String name = product.getName();
        String description = product.getDescription();
        BigDecimal price = product.getPrice().getValue();
        Currency currency = product.getPrice().getCurrency();
        return new ProductEntity(
                id,
                name,
                description,
                price,
                currency);
    }

    @Override
    public Product toModel(ProductEntity entity) throws ValidationException {
        Product product = new Product();
        product.setId(entity.getId());
        product.setName(entity.getName());
        String description = entity.getDescription();
        if (description != null) {
            product.setDescription(description);
        }
        Price productPrice = new Price(entity.getPrice(), entity.getCurrency());
        product.setPrice(productPrice);

        product.validate();
        return product;
    }
}
