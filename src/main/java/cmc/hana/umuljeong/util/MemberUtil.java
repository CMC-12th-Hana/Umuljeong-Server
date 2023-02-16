package cmc.hana.umuljeong.util;

import cmc.hana.umuljeong.domain.Member;

public class MemberUtil {

    public static Member mockMember() {
        return Member.builder().build();
    }
}
