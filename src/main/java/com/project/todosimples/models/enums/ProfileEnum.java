package com.project.todosimples.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@AllArgsConstructor
@Getter
public enum ProfileEnum {

    ADMIN(1, "ROLE_ADMIN"),
    USER(2, "ROLE_USER");

    private final Integer code;
    private final String description;

    public static ProfileEnum toEnum(Integer code) {

        if(Objects.isNull(code))
            return null;

        for (ProfileEnum c : ProfileEnum.values()) {

            if (code.equals(c.getCode()))
                return c;
        }
        throw new IllegalArgumentException("Invalid code value: " + code);
    }
}
