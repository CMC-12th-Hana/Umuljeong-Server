package cmc.hana.umuljeong.domain.enums;

import lombok.Getter;

@Getter
public enum MemberRole {
    LEADER("ROLE_LEADER", "리더"),
    STAFF("ROLE_STAFF", "사원");

    private String authority;
    private String description;

    MemberRole(String authority, String description) {
        this.authority = authority;
        this.description = description;
    }
}
