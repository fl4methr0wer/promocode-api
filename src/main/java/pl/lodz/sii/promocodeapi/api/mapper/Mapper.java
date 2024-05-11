package pl.lodz.sii.promocodeapi.api.mapper;

import pl.lodz.sii.promocodeapi.core.exception.ValidationException;

public interface Mapper<A, B> {
    B map(A source) throws ValidationException;
}