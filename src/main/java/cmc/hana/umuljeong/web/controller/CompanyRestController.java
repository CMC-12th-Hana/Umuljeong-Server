package cmc.hana.umuljeong.web.controller;

import cmc.hana.umuljeong.auth.annotation.AuthUser;
import cmc.hana.umuljeong.converter.CompanyConverter;
import cmc.hana.umuljeong.domain.Company;
import cmc.hana.umuljeong.domain.Member;
import cmc.hana.umuljeong.exception.CompanyException;
import cmc.hana.umuljeong.exception.common.ApiErrorResult;
import cmc.hana.umuljeong.exception.common.ErrorCode;
import cmc.hana.umuljeong.service.CompanyService;
import cmc.hana.umuljeong.web.dto.ClientCompanyResponseDto;
import cmc.hana.umuljeong.web.dto.CompanyRequestDto;
import cmc.hana.umuljeong.web.dto.CompanyResponseDto;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Tag(name = "Company API", description = "회사 추가")
@Validated
@RestController
@RequiredArgsConstructor
public class CompanyRestController {

    private final CompanyService companyService;

    @Operation(summary = "[001_05]", description = "회사 추가")
    @Parameters({
            @Parameter(name = "member", hidden = true)
    })
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "CREATED : 정상응답", content = @Content(schema = @Schema(implementation = ClientCompanyResponseDto.ClientCompanyListDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST : 요청 데이터의 값이 형식에 맞지 않은 경우", content = @Content(schema = @Schema(implementation = ApiErrorResult.class))),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED : 인증되지 않은 사용자", content = @Content(schema = @Schema(implementation = ApiErrorResult.class))),
    })
    @PostMapping("/company")
    public ResponseEntity<CompanyResponseDto.CompanyCreateDto> createCompany(@RequestBody @Valid CompanyRequestDto.CompanyCreateDto request, @AuthUser Member member) {
        if(member.getCompany() != null) throw new CompanyException(ErrorCode.COMPANY_ALREADY_EXISTS);
        Company company = companyService.create(member, request);
        return ResponseEntity.ok(CompanyConverter.toCompanyCreateDto(company));
    }
}
