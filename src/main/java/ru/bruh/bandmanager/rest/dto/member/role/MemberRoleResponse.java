package ru.bruh.bandmanager.rest.dto.member.role;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class MemberRoleResponse {

    private Long id;
    private String name;
}
