package cmc.hana.umuljeong.domain.embedded;

import lombok.*;

import javax.persistence.Embeddable;
import java.time.LocalDate;

@Embeddable
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BusinessPeriod {
    private LocalDate start;
    private LocalDate finish;
}
