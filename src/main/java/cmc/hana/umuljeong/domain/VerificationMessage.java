package cmc.hana.umuljeong.domain;

import cmc.hana.umuljeong.domain.common.BaseEntity;
import cmc.hana.umuljeong.domain.enums.VerifyMessageStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VerificationMessage extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String phoneNumber;

    private String verificationNumber;

    private LocalDateTime ExpirationTime;

    @Enumerated(EnumType.STRING)
    private VerifyMessageStatus verificationJoin;

    @Enumerated(EnumType.STRING)
    private VerifyMessageStatus verificationPassword;
}
