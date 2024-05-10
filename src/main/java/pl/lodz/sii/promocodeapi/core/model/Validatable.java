package pl.lodz.sii.promocodeapi.core.model;

import pl.lodz.sii.promocodeapi.core.exception.ValidationException;

public interface Validatable {
    void validate() throws ValidationException;
}
