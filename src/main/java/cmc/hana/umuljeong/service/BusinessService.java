package cmc.hana.umuljeong.service;

import cmc.hana.umuljeong.domain.Business;
import cmc.hana.umuljeong.domain.ClientCompany;
import cmc.hana.umuljeong.web.dto.BusinessRequestDto;

import java.util.List;

public interface BusinessService {
    Business create(Long clientCompanyId, BusinessRequestDto.CreateBusinessDto request);

    Business findById(Long businessId);

    List<Business> findByClientCompany(ClientCompany clientCompany);

    Business update(Long businessId, BusinessRequestDto.UpdateBusinessDto request);
}
