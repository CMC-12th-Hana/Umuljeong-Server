package cmc.hana.umuljeong.web.controller;

import cmc.hana.umuljeong.auth.annotation.AuthUser;
import cmc.hana.umuljeong.converter.BusinessConverter;
import cmc.hana.umuljeong.domain.Business;
import cmc.hana.umuljeong.domain.ClientCompany;
import cmc.hana.umuljeong.domain.Member;
import cmc.hana.umuljeong.domain.mapping.BusinessMember;
import cmc.hana.umuljeong.service.BusinessMemberService;
import cmc.hana.umuljeong.service.BusinessService;
import cmc.hana.umuljeong.service.ClientCompanyService;
import cmc.hana.umuljeong.service.MemberService;
import cmc.hana.umuljeong.validation.annotation.ExistBusiness;
import cmc.hana.umuljeong.validation.annotation.ExistClientCompany;
import cmc.hana.umuljeong.web.dto.BusinessRequestDto;
import cmc.hana.umuljeong.web.dto.BusinessResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Business API", description = "사업 조회, 추가")
@Validated
@RestController
@RequiredArgsConstructor
public class BusinessRestController {

    private final BusinessService businessService;
    private final ClientCompanyService clientCompanyService;
    private final BusinessMemberService businessMemberService;

    @Operation(summary = "[003_04]", description = "사업 조회")
    @GetMapping("/company/client/business/{businessId}")
    public ResponseEntity<BusinessResponseDto.BusinessDto> getBusiness(@PathVariable(name = "businessId") @ExistBusiness Long businessId, @AuthUser Member member) {
        // todo : 해당 사업이 멤버의 회사에 속한 사업인지 검증
        Business business = businessService.findById(businessId);
        return ResponseEntity.ok(BusinessConverter.toBusinessDto(business));
    }

    @GetMapping("/company/client/{clientId}/businesses")
    public ResponseEntity<BusinessResponseDto.BusinessListDto> getBusinessList(@PathVariable(name = "clientId") @ExistClientCompany Long clientCompanyId, @AuthUser Member member) {
        // todo : 존재하는 id 인지 & 멤버의 회사에 속한 고객사인지 검증
        ClientCompany clientCompany = clientCompanyService.findById(clientCompanyId);
        List<Business> businessList = businessService.findByClientCompany(clientCompany);
        return ResponseEntity.ok(BusinessConverter.toBusinessListDto(businessList));
    }

    @Operation(summary = "[003_04_1]", description = "사업 추가")
    @PostMapping("/company/client/{clientId}/business")
    public ResponseEntity<BusinessResponseDto.CreateBusinessDto> createBusiness(@PathVariable(name = "clientId") @ExistClientCompany Long clientCompanyId, @RequestBody BusinessRequestDto.CreateBusinessDto request) {
        Business business = businessService.create(clientCompanyId, request);
        return ResponseEntity.ok(BusinessConverter.toCreateBusinessDto(business));
    }

    @Operation(summary = "[003_04_2]", description = "사업 수정")
    @PatchMapping("/company/client/business/{businessId}")
    public ResponseEntity<BusinessResponseDto.UpdateBusinessDto> updateBusiness(@PathVariable(name = "businessId") @ExistBusiness Long businessId, @RequestBody BusinessRequestDto.UpdateBusinessDto request, @AuthUser Member member) {
        Business business = businessService.update(businessId, request);
        return ResponseEntity.ok(BusinessConverter.toUpdateBusinessDto(business));
    }
}
