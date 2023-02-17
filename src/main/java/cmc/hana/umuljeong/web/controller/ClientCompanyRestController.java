package cmc.hana.umuljeong.web.controller;

import cmc.hana.umuljeong.auth.annotation.AuthUser;
import cmc.hana.umuljeong.converter.ClientCompanyConverter;
import cmc.hana.umuljeong.domain.ClientCompany;
import cmc.hana.umuljeong.domain.Member;
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
    public ResponseEntity<ClientCompanyResponseDto.ClientCompanyListDto> getClientCompanyList(@AuthUser Member member) {
        return null;
    }

    @GetMapping("/company/client/{clientId}")
    public ResponseEntity<ClientCompanyResponseDto.ClientCompanyDto> getClientCompany(@PathVariable(name = "clientId") Long clientCompanyId, @AuthUser Member member) {
        // todo : 해당 멤버가 속한 회사의 고객사인지 & 존재하는 id 인지 검증 필요
        ClientCompany clientCompany = clientCompanyService.findById(clientCompanyId);
        return ResponseEntity.ok(ClientCompanyConverter.toClientCompanyDto(clientCompany));
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
