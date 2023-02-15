package cmc.hana.umuljeong.web.controller;

import cmc.hana.umuljeong.converter.ClientCompanyConverter;
import cmc.hana.umuljeong.domain.ClientCompany;
import cmc.hana.umuljeong.service.ClientCompanyService;
import cmc.hana.umuljeong.web.dto.ClientCompanyRequestDto;
import cmc.hana.umuljeong.web.dto.ClientCompanyResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ClientCompanyRestController {

    private final ClientCompanyService clientCompanyService;

    @GetMapping("/company/clients")
    public ResponseEntity<ClientCompanyResponseDto.ClientCompanyListDto> getClientCompanyList() {
        return null;
    }

    @PostMapping("/company/client")
    public ResponseEntity<ClientCompanyResponseDto.CreateClientCompany> createClientCompany(@RequestBody ClientCompanyRequestDto.CreateClientCompanyDto request) {
        ClientCompany clientCompany = clientCompanyService.create(request);
        return ResponseEntity.ok(ClientCompanyConverter.toCreateClientCompany(clientCompany));
    }

    @PatchMapping("/company/client")
    public ResponseEntity<ClientCompanyResponseDto.UpdateClientCompany> updateClientCompany() {
        return null;
    }


}
