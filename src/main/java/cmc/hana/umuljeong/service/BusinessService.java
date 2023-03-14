package cmc.hana.umuljeong.service;

import cmc.hana.umuljeong.domain.Business;
import cmc.hana.umuljeong.domain.Member;
import cmc.hana.umuljeong.web.dto.BusinessRequestDto;

import java.time.LocalDate;
import java.util.List;

public interface BusinessService {
    Business create(Long clientCompanyId, BusinessRequestDto.CreateBusinessDto request);

    Business findById(Long businessId);

    List<Business> findByClientCompany(Long clientCompanyId);

    Business update(Long businessId, BusinessRequestDto.UpdateBusinessDto request);

    void delete(Long businessId);

    List<Business> findByCompanyAndNameAndStartAndFinishAndMember(Long companyId, String name, LocalDate start, LocalDate finish, Member member);

    List<Business> findByClientCompanyAndNameAndStartAndFinishAndMember(Long clientCompanyId, String name, LocalDate start, LocalDate finish, Member member);
}
