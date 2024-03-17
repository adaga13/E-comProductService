package org.scaler.ecommerce.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDto {

    private String name;

    private String email;

    private List<Role> roles;

    @Getter
    @Setter
    static class Role  {
        private String name;
    }
}
