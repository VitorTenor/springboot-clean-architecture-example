package com.vitortenorio.springbootcleanarchitectureexample.api.v1.assembler;

import com.vitortenorio.springbootcleanarchitectureexample.api.v1.output.UserOutput;
import com.vitortenorio.springbootcleanarchitectureexample.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserAssembler  implements Assembler<UserEntity, UserOutput>{

    @Override
    public UserOutput toOutput(UserEntity entity) {
        return UserOutput.builder()
                .uuid(entity.uuid())
                .name(entity.name())
                .lastName(entity.lastName())
                .email(entity.email())
                .build();
    }
}
