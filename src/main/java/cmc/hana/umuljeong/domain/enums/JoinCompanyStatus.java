package cmc.hana.umuljeong.domain.enums;

public enum JoinCompanyStatus {
    JOINED("합류완료"),
    PENDING("합류전");

    private String description;

    JoinCompanyStatus(String description) {
        this.description = description;
    }
}
