package cmc.hana.umuljeong.service.impl;

import cmc.hana.umuljeong.domain.Business;
import cmc.hana.umuljeong.domain.ClientCompany;
import cmc.hana.umuljeong.repository.BusinessRepository;
import cmc.hana.umuljeong.service.BusinessService;
import cmc.hana.umuljeong.web.dto.BusinessRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BusinessServiceImpl implements BusinessService {

    private final BusinessRepository businessRepository;

    @Transactional
    @Override
    public Business create(BusinessRequestDto.CreateBusinessDto request) {
        return null;
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
