package com.jinhee2.mapper;

public interface EntityMapper<D, E> {
    D toDto(E entity);

    E toEntity(D dto);
}
