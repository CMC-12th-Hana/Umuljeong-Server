package cmc.hana.umuljeong.web.controller;

import cmc.hana.umuljeong.web.dto.CompanyResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompanyRestController {

    @PostMapping("/company")
    public ResponseEntity<CompanyResponseDto.CompanyCreateDto> createCompany() {
        return null;
    }
}
