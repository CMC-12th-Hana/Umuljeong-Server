package cmc.hana.umuljeong.domain.enums;

public enum VerifyMessageStatus {
    PENDING("인증전"),
    VERIFIED("인증완료"),
    FAILED("인증실패");

    private String description;

    VerifyMessageStatus(String description) {
        this.description = description;
    }
}