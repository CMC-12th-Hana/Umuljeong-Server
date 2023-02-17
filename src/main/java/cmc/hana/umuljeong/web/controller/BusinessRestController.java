package cmc.hana.umuljeong.web.controller;

import cmc.hana.umuljeong.auth.annotation.AuthUser;
import cmc.hana.umuljeong.converter.BusinessConverter;
import cmc.hana.umuljeong.domain.Business;
import cmc.hana.umuljeong.domain.Member;
import cmc.hana.umuljeong.domain.mapping.BusinessMember;
import cmc.hana.umuljeong.service.BusinessMemberService;
import cmc.hana.umuljeong.service.BusinessService;
import cmc.hana.umuljeong.service.MemberService;
import cmc.hana.umuljeong.web.dto.BusinessRequestDto;
import cmc.hana.umuljeong.web.dto.BusinessResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BusinessRestController {

    private final BusinessService businessService;

    private final BusinessMemberService businessMemberService;

    @GetMapping("/company/client/business/{businessId}")
    public ResponseEntity<BusinessResponseDto.BusinessDto> getBusiness(@PathVariable(name = "businessId") Long businessId, @AuthUser Member member) {
        Business business = businessService.findById(businessId);
        List<BusinessMember> businessMemberList = businessMemberService.findByBusiness(business);
        return ResponseEntity.ok(BusinessConverter.toBusinessDto(business, businessMemberList));
    }

    @GetMapping("/company/client/businesses")
    public ResponseEntity<BusinessResponseDto.BusinessListDto> getBusinessList() {
        return null;
    }

    @PostMapping("/company/client/business")
    public ResponseEntity<BusinessResponseDto.CreateBusinessDto> createBusiness(@RequestBody BusinessRequestDto.CreateBusinessDto request) {
        Business business = businessService.create(request);
        return ResponseEntity.ok(BusinessConverter.toCreateBusinessDto(business));
    }
}
