package cmc.hana.umuljeong.service;

import cmc.hana.umuljeong.domain.Business;
import cmc.hana.umuljeong.web.dto.BusinessRequestDto;

public interface BusinessService {
    Business create(BusinessRequestDto.CreateBusinessDto request);
}
