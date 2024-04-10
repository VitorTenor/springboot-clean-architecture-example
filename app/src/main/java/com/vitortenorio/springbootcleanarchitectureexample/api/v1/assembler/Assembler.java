package com.vitortenorio.springbootcleanarchitectureexample.api.v1.assembler;

import java.util.List;

public interface Assembler <E, O>{
    O toOutput(E entity);

    default List<O> toOutputList(List<E> entities) {
        return entities.stream()
                .map(this::toOutput)
                .toList();
    }
}
