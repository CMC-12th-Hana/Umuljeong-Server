package cmc.hana.umuljeong.web.controller;

import cmc.hana.umuljeong.auth.annotation.AuthUser;
import cmc.hana.umuljeong.converter.BusinessConverter;
import cmc.hana.umuljeong.domain.Business;
import cmc.hana.umuljeong.domain.ClientCompany;
import cmc.hana.umuljeong.domain.Member;
import cmc.hana.umuljeong.domain.mapping.BusinessMember;
import cmc.hana.umuljeong.exception.BusinessException;
import cmc.hana.umuljeong.exception.ClientCompanyException;
import cmc.hana.umuljeong.exception.CompanyException;
import cmc.hana.umuljeong.exception.common.ApiErrorResult;
import cmc.hana.umuljeong.exception.common.ErrorCode;
import cmc.hana.umuljeong.service.BusinessMemberService;
import cmc.hana.umuljeong.service.BusinessService;
import cmc.hana.umuljeong.service.ClientCompanyService;
import cmc.hana.umuljeong.service.MemberService;
import cmc.hana.umuljeong.validation.annotation.ExistBusiness;
import cmc.hana.umuljeong.validation.annotation.ExistClientCompany;
import cmc.hana.umuljeong.validation.annotation.ExistCompany;
import cmc.hana.umuljeong.validation.validator.BusinessValidator;
import cmc.hana.umuljeong.validation.validator.ClientCompanyValidator;
import cmc.hana.umuljeong.validation.validator.CompanyValidator;
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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
        if(!BusinessValidator.isAccessible(member, businessId)) throw new BusinessException(ErrorCode.BUSINESS_ACCESS_DENIED);

        Business business = businessService.findById(businessId);
        return ResponseEntity.ok(BusinessConverter.toBusinessDto(business));
    }

    // todo : 사업명, 기간 검색 가능하게
    @Operation(summary = "[003_03]", description = "사업 조회 by 고객사")
    @Parameters({
            @Parameter(name = "member", hidden = true)
    })
    @GetMapping("/company/client/{clientId}/businesses")
    public ResponseEntity<BusinessResponseDto.BusinessListDto> getBusinessList(@PathVariable(name = "clientId") @ExistClientCompany Long clientCompanyId, @AuthUser Member member) {
        if(!ClientCompanyValidator.isAccessible(member, clientCompanyId))
            throw new ClientCompanyException(ErrorCode.CLIENT_COMPANY_ACCESS_DENIED);

        List<Business> businessList = businessService.findByClientCompany(clientCompanyId);
        return ResponseEntity.ok(BusinessConverter.toBusinessListDto(businessList));
    }

    @Operation(summary = "[004_01]", description = "사업 검색")
    @Parameters({
            @Parameter(name = "member", hidden = true)
    })
    @GetMapping("/company/{companyId}/client/businesses")
    public ResponseEntity<BusinessResponseDto.BusinessListDto> searchBusinessList(@PathVariable(name = "companyId") @ExistCompany Long companyId,
                                                                                  @RequestParam(name = "name", required = false) String name,
                                                                                  @RequestParam(name = "start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
                                                                                  @RequestParam(name = "finish") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate finish,
                                                                                  @AuthUser Member member) {
        if(!CompanyValidator.isAccessible(member, companyId)) throw new CompanyException(ErrorCode.COMPANY_ACCESS_DENIED);

        List<Business> businessList = businessService.findByCompanyAndNameAndStartAndFinishAndMember(companyId, name, start, finish, member);
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
        if(!ClientCompanyValidator.isAccessible(member,clientCompanyId))
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
        if(!BusinessValidator.isAccessible(member, businessId)) throw new BusinessException(ErrorCode.BUSINESS_ACCESS_DENIED);

        Business business = businessService.update(businessId, request);
        return ResponseEntity.ok(BusinessConverter.toUpdateBusinessDto(business));
    }

    @Operation(summary = "[003_04]", description = "사업 삭제")
    @Parameters({
            @Parameter(name = "member", hidden = true)
    })
    @DeleteMapping("/company/client/business/{businessId}")
    public ResponseEntity<BusinessResponseDto.DeleteBusinessDto> deleteBusiness(@PathVariable (name = "businessId") @ExistBusiness Long businessId, @AuthUser Member member) {
        if(!BusinessValidator.isAccessible(member, businessId)) throw new BusinessException(ErrorCode.BUSINESS_ACCESS_DENIED);

        businessService.delete(businessId);
        return ResponseEntity.ok(BusinessConverter.DeleteBusinessDto());
    }
}
