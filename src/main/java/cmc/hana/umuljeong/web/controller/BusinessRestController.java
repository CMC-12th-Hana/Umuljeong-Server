package cmc.hana.umuljeong.web.controller;

import cmc.hana.umuljeong.auth.annotation.AuthUser;
import cmc.hana.umuljeong.converter.BusinessConverter;
import cmc.hana.umuljeong.domain.Business;
import cmc.hana.umuljeong.domain.ClientCompany;
import cmc.hana.umuljeong.domain.Member;
import cmc.hana.umuljeong.domain.mapping.BusinessMember;
import cmc.hana.umuljeong.exception.BusinessException;
import cmc.hana.umuljeong.exception.ClientCompanyException;
import cmc.hana.umuljeong.exception.common.ApiErrorResult;
import cmc.hana.umuljeong.exception.common.ErrorCode;
import cmc.hana.umuljeong.service.BusinessMemberService;
import cmc.hana.umuljeong.service.BusinessService;
import cmc.hana.umuljeong.service.ClientCompanyService;
import cmc.hana.umuljeong.service.MemberService;
import cmc.hana.umuljeong.validation.annotation.ExistBusiness;
import cmc.hana.umuljeong.validation.annotation.ExistClientCompany;
import cmc.hana.umuljeong.web.dto.BusinessRequestDto;
import cmc.hana.umuljeong.web.dto.BusinessResponseDto;
import cmc.hana.umuljeong.web.dto.ClientCompanyResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    @Parameters({
            @Parameter(name = "member", hidden = true)
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK : 정상응답", content = @Content(schema = @Schema(implementation = BusinessResponseDto.BusinessDto.class))),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED : 인증되지 않은 사용자", content = @Content(schema = @Schema(implementation = ApiErrorResult.class))),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN : 본인 회사 고객사의 사업이 아닌 경우", content = @Content(schema = @Schema(implementation = ApiErrorResult.class))),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND : businessId에 해당하는 고객사가 존재하지 않는 경우", content = @Content(schema = @Schema(implementation = ApiErrorResult.class)))
    })
    @GetMapping("/company/client/business/{businessId}")
    public ResponseEntity<BusinessResponseDto.BusinessDto> getBusiness(@PathVariable(name = "businessId") @ExistBusiness Long businessId, @AuthUser Member member) {
        boolean isValid = false;
        for(ClientCompany clientCompany : member.getCompany().getClientCompanyList()) {
            isValid = clientCompany.getBusinessList().stream().anyMatch(business -> business.getId() == businessId);
            if(isValid) break;
        }
        if(!isValid) throw new BusinessException(ErrorCode.BUSINESS_ACCESS_DENIED);

        Business business = businessService.findById(businessId);
        return ResponseEntity.ok(BusinessConverter.toBusinessDto(business));
    }

    @Deprecated
    @GetMapping("/company/client/{clientId}/businesses")
    public ResponseEntity<BusinessResponseDto.BusinessListDto> getBusinessList(@PathVariable(name = "clientId") @ExistClientCompany Long clientCompanyId, @AuthUser Member member) {
        // todo : 멤버의 회사에 속한 고객사인지 검증
        ClientCompany clientCompany = clientCompanyService.findById(clientCompanyId);
        List<Business> businessList = businessService.findByClientCompany(clientCompany);
        return ResponseEntity.ok(BusinessConverter.toBusinessListDto(businessList));
    }

    @Operation(summary = "[003_04_1]", description = "사업 추가")
    @Parameters({
            @Parameter(name = "member", hidden = true)
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK : 정상응답", content = @Content(schema = @Schema(implementation = BusinessResponseDto.CreateBusinessDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST : 요청 데이터의 값이 형식에 맞지 않은 경우", content = @Content(schema = @Schema(implementation = ApiErrorResult.class))),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED : 인증되지 않은 사용자", content = @Content(schema = @Schema(implementation = ApiErrorResult.class))),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN : 본인 회사의 고객사가 아닌 경우", content = @Content(schema = @Schema(implementation = ApiErrorResult.class))),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND : clientId에 해당하는 고객사가 존재하지 않는 경우", content = @Content(schema = @Schema(implementation = ApiErrorResult.class)))
    })
    @PostMapping("/company/client/{clientId}/business")
    public ResponseEntity<BusinessResponseDto.CreateBusinessDto> createBusiness(@PathVariable(name = "clientId") @ExistClientCompany Long clientCompanyId, @RequestBody @Valid BusinessRequestDto.CreateBusinessDto request, @AuthUser Member member) {
        if(!member.getCompany().getClientCompanyList().stream().anyMatch(clientCompany -> clientCompany.getId() == clientCompanyId))
            throw new ClientCompanyException(ErrorCode.CLIENT_COMPANY_ACCESS_DENIED);

        Business business = businessService.create(clientCompanyId, request);
        return ResponseEntity.ok(BusinessConverter.toCreateBusinessDto(business));
    }

    @Operation(summary = "[003_04_2]", description = "사업 수정")
    @Parameters({
            @Parameter(name = "member", hidden = true)
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK : 정상응답", content = @Content(schema = @Schema(implementation = BusinessResponseDto.UpdateBusinessDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST : 요청 데이터의 값이 형식에 맞지 않은 경우", content = @Content(schema = @Schema(implementation = ApiErrorResult.class))),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED : 인증되지 않은 사용자", content = @Content(schema = @Schema(implementation = ApiErrorResult.class))),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN : 본인 회사 고객사의 사업이 아닌 경우", content = @Content(schema = @Schema(implementation = ApiErrorResult.class))),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND : businessId에 해당하는 고객사가 존재하지 않는 경우", content = @Content(schema = @Schema(implementation = ApiErrorResult.class)))
    })
    @PatchMapping("/company/client/business/{businessId}")
    public ResponseEntity<BusinessResponseDto.UpdateBusinessDto> updateBusiness(@PathVariable(name = "businessId") @ExistBusiness Long businessId, @RequestBody @Valid BusinessRequestDto.UpdateBusinessDto request, @AuthUser Member member) {
        boolean isValid = false;
        for(ClientCompany clientCompany : member.getCompany().getClientCompanyList()) {
            isValid = clientCompany.getBusinessList().stream().anyMatch(business -> business.getId() == businessId);
            if(isValid) break;
        }
        if(!isValid) throw new BusinessException(ErrorCode.BUSINESS_ACCESS_DENIED);

        Business business = businessService.update(businessId, request);
        return ResponseEntity.ok(BusinessConverter.toUpdateBusinessDto(business));
    }

    @Operation(summary = "[003_04]", description = "사업 삭제")
    @Parameters({
            @Parameter(name = "member", hidden = true)
    })
    @DeleteMapping("/company/client/business/{businessId}")
    public ResponseEntity<BusinessResponseDto.DeleteBusinessDto> deleteBusiness(@PathVariable (name = "businessId") @ExistBusiness Long businessId, @AuthUser Member member) {
        boolean isValid = false;
        for(ClientCompany clientCompany : member.getCompany().getClientCompanyList()) {
            isValid = clientCompany.getBusinessList().stream().anyMatch(business -> business.getId() == businessId);
            if(isValid) break;
        }
        if(!isValid) throw new BusinessException(ErrorCode.BUSINESS_ACCESS_DENIED);

        businessService.delete(businessId);
        return ResponseEntity.ok(BusinessConverter.DeleteBusinessDto());
    }
}
