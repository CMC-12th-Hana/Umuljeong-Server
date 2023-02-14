package cmc.hana.umuljeong.web.controller;

import cmc.hana.umuljeong.web.dto.ClientCompanyResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientCompanyRestController {

    @GetMapping("/company/clients")
    public ResponseEntity<ClientCompanyResponseDto.ClientCompanyListDto> getClientCompanyList() {
        return null;
    }

    @PostMapping("/company/client")
    public ResponseEntity<ClientCompanyResponseDto.CreateClientCompany> createClientCompany() {
        return null;
    }

    @PatchMapping("/company/client")
    public ResponseEntity<ClientCompanyResponseDto.UpdateClientCompany> updateClientCompany() {
        return null;
    }


}
