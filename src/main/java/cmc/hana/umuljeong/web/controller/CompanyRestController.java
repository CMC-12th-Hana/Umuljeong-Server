package cmc.hana.umuljeong.web.controller;

import cmc.hana.umuljeong.auth.annotation.AuthUser;
import cmc.hana.umuljeong.converter.CompanyConverter;
import cmc.hana.umuljeong.domain.Company;
import cmc.hana.umuljeong.domain.Member;
import cmc.hana.umuljeong.service.CompanyService;
import cmc.hana.umuljeong.web.dto.CompanyRequestDto;
import cmc.hana.umuljeong.web.dto.CompanyResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Company API", description = "회사 추가")
@Validated
@RestController
@RequiredArgsConstructor
public class CompanyRestController {

    private final CompanyService companyService;

    // todo : 이미 회사에 소속되어 있으면 생성할 수 없도록
    @Operation(summary = "[001_05]", description = "회사 추가")
    @PostMapping("/company")
    public ResponseEntity<CompanyResponseDto.CompanyCreateDto> createCompany(@RequestBody CompanyRequestDto.CompanyCreateDto request, @AuthUser Member member) {
        Company company = companyService.create(member, request);
        return ResponseEntity.ok(CompanyConverter.toCompanyCreateDto(company));
    }
}
