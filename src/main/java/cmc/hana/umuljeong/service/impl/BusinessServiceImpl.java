package cmc.hana.umuljeong.service.impl;

import cmc.hana.umuljeong.converter.BusinessConverter;
import cmc.hana.umuljeong.converter.BusinessMemberConverter;
import cmc.hana.umuljeong.domain.Business;
import cmc.hana.umuljeong.domain.ClientCompany;
import cmc.hana.umuljeong.domain.mapping.BusinessMember;
import cmc.hana.umuljeong.repository.BusinessRepository;
import cmc.hana.umuljeong.repository.ClientCompanyRepository;
import cmc.hana.umuljeong.repository.mapping.BusinessMemberRepository;
import cmc.hana.umuljeong.service.BusinessService;
import cmc.hana.umuljeong.web.dto.BusinessRequestDto;
import cmc.hana.umuljeong.web.dto.BusinessResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BusinessServiceImpl implements BusinessService {

    private final BusinessRepository businessRepository;
    private final ClientCompanyRepository clientCompanyRepository;
    private final BusinessMemberRepository businessMemberRepository;

    @Transactional
    @Override
    public Business create(Long clientCompanyId, BusinessRequestDto.CreateBusinessDto request) {
        ClientCompany clientCompany = clientCompanyRepository.findById(clientCompanyId).get();
        Business business = BusinessConverter.toBusiness(clientCompany, request);
        return businessRepository.save(business);
    }

    @Transactional
    @Override
    public Business update(Long businessId, BusinessRequestDto.UpdateBusinessDto request) {
        Business business = businessRepository.findById(businessId).get();
        List<BusinessMember> originalBusinessMemberList = businessMemberRepository.findByBusiness(business);
        originalBusinessMemberList.stream()
                .forEach(businessMember -> businessMemberRepository.delete(businessMember));
        List<BusinessMember> newBusinessMemberList = request.getMemberIdList().stream()
                .map(memberId -> {
                    BusinessMember businessMember = BusinessMemberConverter.toBusinessMember(business, memberId);
                    return businessMemberRepository.save(businessMember);
                })
                .collect(Collectors.toList());

        business.setBusinessMemberList(newBusinessMemberList);
        business.setBusinessPeriod(BusinessConverter.toBusinessPeriod(request.getBusinessPeriodDto()));
        business.setDescription(request.getDescription());
        business.setName(request.getName());
        business.setRevenue(request.getRevenue());

        return business;
    }

    @Transactional
    @Override
    public void delete(Long businessId) {
        Business business = businessRepository.findById(businessId).get();
        business.removeRelationship();
        businessRepository.delete(business);
        return ;
    }

    @Override
    public Business findById(Long businessId) {
        return businessRepository.findById(businessId).get();
    }

    @Override
    public List<Business> findByClientCompany(ClientCompany clientCompany) {
        return businessRepository.findByClientCompany(clientCompany);
    }
}
