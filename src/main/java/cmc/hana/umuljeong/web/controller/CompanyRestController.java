package cmc.hana.umuljeong.web.controller;

import cmc.hana.umuljeong.converter.CompanyConverter;
import cmc.hana.umuljeong.domain.Company;
import cmc.hana.umuljeong.service.CompanyService;
import cmc.hana.umuljeong.web.dto.CompanyRequestDto;
import cmc.hana.umuljeong.web.dto.CompanyResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CompanyRestController {

    private final CompanyService companyService;

    @PostMapping("/company")
    public ResponseEntity<CompanyResponseDto.CompanyCreateDto> createCompany(@RequestBody CompanyRequestDto.CompanyCreateDto request) {
        Company company = companyService.create(request);
        return ResponseEntity.ok(CompanyConverter.toCompanyCreateDto(company));
    }
}
