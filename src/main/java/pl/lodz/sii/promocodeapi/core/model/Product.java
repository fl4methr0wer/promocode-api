package pl.lodz.sii.promocodeapi.core.model;

import lombok.*;
import pl.lodz.sii.promocodeapi.core.exception.ValidationException;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Product implements Validatable {
    private Long id;
    private String name = "";
    private String description = "";
    private Price price;

    @Override
    public void validate() throws ValidationException {
        if (name == null || name.isBlank() || name.isEmpty()) {
            throw new ValidationException("Product name length should not be empty");
        }
        price.validate();
    }
}

