package pl.lodz.sii.promocodeapi.core.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.lodz.sii.promocodeapi.core.exception.ValidationException;

@Data
@ToString
@EqualsAndHashCode
public class Product implements Validatable<Product> {
    private Long id;
    private String name;
    private Price price;

    @Override
    public void validate() throws ValidationException {
        if (name == null || name.isBlank() || name.isEmpty()) {
            throw new ValidationException("Product name length should not be empty");
        }
        price.validate();
    }

}

