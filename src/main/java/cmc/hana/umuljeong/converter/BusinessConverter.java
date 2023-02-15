package cmc.hana.umuljeong.converter;

import cmc.hana.umuljeong.domain.Business;
import cmc.hana.umuljeong.web.dto.BusinessResponseDto;

public class BusinessConverter {

    public static BusinessResponseDto.CreateBusinessDto toCreateBusinessDto(Business business) {
        return BusinessResponseDto.CreateBusinessDto.builder()
                .businessId(business.getId())
                .createdAt(business.getCreatedAt())
                .build();
    }
}
