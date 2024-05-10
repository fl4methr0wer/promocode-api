package pl.lodz.sii.promocodeapi.core.model;

import pl.lodz.sii.promocodeapi.core.exception.ValidationException;

public interface Validatable<Type> {
    void validate() throws ValidationException;
}
