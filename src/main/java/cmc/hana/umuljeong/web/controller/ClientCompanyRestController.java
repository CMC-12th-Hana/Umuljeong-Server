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

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ClientCompanyRestController {

    private final ClientCompanyService clientCompanyService;

    @GetMapping("/company/clients")
    public ResponseEntity<ClientCompanyResponseDto.ClientCompanyListDto> getClientCompanyList(@AuthUser Member member) {
        List<ClientCompany> clientCompanyList = clientCompanyService.findByCompany(member.getCompany());
        return ResponseEntity.ok(ClientCompanyConverter.toClientCompanyListDto(clientCompanyList));
    }

    @GetMapping("/company/client/{clientId}")
    public ResponseEntity<ClientCompanyResponseDto.ClientCompanyDto> getClientCompany(@PathVariable(name = "clientId") Long clientCompanyId, @AuthUser Member member) {
        // todo : 해당 멤버가 속한 회사의 고객사인지 & 존재하는 id 인지 검증 필요
        ClientCompany clientCompany = clientCompanyService.findById(clientCompanyId);
        return ResponseEntity.ok(ClientCompanyConverter.toClientCompanyDto(clientCompany));
    }

    @PostMapping("/company/client")
    public ResponseEntity<ClientCompanyResponseDto.CreateClientCompany> createClientCompany(@RequestBody ClientCompanyRequestDto.CreateClientCompanyDto request, @AuthUser Member member) {
        ClientCompany clientCompany = clientCompanyService.create(request, member);
        return ResponseEntity.ok(ClientCompanyConverter.toCreateClientCompany(clientCompany));
    }

    @PatchMapping("/company/client/{clientId}")
    public ResponseEntity<ClientCompanyResponseDto.UpdateClientCompany> updateClientCompany(@PathVariable(name = "clientId") Long clientCompanyId, @RequestBody ClientCompanyRequestDto.UpdateClientCompanyDto request, @AuthUser Member member) {
        // todo : 해당 멤버가 속한 회사의 고객사인지 & 존재하는 id 인지 검증 필요
        ClientCompany clientCompany = clientCompanyService.update(clientCompanyId, request, member);
        return ResponseEntity.ok(ClientCompanyConverter.toUpdateClientCompany(clientCompany));
    }

}
