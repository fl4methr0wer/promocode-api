package pl.lodz.sii.promocodeapi.persistence.mapper;

import pl.lodz.sii.promocodeapi.core.exception.ValidationException;

public interface ModelEntityMapper<Model, Entity> {
    Entity toEntity(Model model);
    Model toModel(Entity entity) throws ValidationException;
}