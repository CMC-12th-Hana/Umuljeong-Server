package cmc.hana.umuljeong.web.controller;

import cmc.hana.umuljeong.auth.annotation.AuthUser;
import cmc.hana.umuljeong.converter.ClientCompanyConverter;
import cmc.hana.umuljeong.domain.ClientCompany;
import cmc.hana.umuljeong.domain.Member;
import cmc.hana.umuljeong.service.ClientCompanyService;
import cmc.hana.umuljeong.validation.annotation.ExistClientCompany;
import cmc.hana.umuljeong.validation.annotation.ExistCompany;
import cmc.hana.umuljeong.web.dto.ClientCompanyRequestDto;
import cmc.hana.umuljeong.web.dto.ClientCompanyResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "ClientCompany API", description = "고객사 조회, 추가, 수정")
@Validated
@RestController
@RequiredArgsConstructor
public class ClientCompanyRestController {

    private final ClientCompanyService clientCompanyService;

    @Operation(summary = "[003_01]", description = "고객사 목록 조회")
    @Parameters({
            @Parameter(name = "member", hidden = true)
    })
    @GetMapping("/company/{companyId}/clients")
    public ResponseEntity<ClientCompanyResponseDto.ClientCompanyListDto> getClientCompanyList(@PathVariable(name = "companyId") @ExistCompany Long companyId, @AuthUser Member member) {
        List<ClientCompany> clientCompanyList = clientCompanyService.findByCompany(companyId);
        return ResponseEntity.ok(ClientCompanyConverter.toClientCompanyListDto(clientCompanyList));
    }

    @Operation(summary = "[003_03]", description = "고객사 조회")
    @Parameters({
            @Parameter(name = "member", hidden = true)
    })
    @GetMapping("/company/client/{clientId}")
    public ResponseEntity<ClientCompanyResponseDto.ClientCompanyDto> getClientCompany(@PathVariable(name = "clientId") @ExistClientCompany Long clientCompanyId, @AuthUser Member member) {
        // todo : 해당 멤버가 속한 회사의 고객사인지 & 존재하는 id 인지 검증 필요
        ClientCompany clientCompany = clientCompanyService.findById(clientCompanyId);
        return ResponseEntity.ok(ClientCompanyConverter.toClientCompanyDto(clientCompany));
    }

    @Operation(summary = "[003_02]", description = "고객사 추가")
    @Parameters({
            @Parameter(name = "member", hidden = true)
    })
    @PostMapping("/company/{companyId}/client")
    public ResponseEntity<ClientCompanyResponseDto.CreateClientCompany> createClientCompany(@PathVariable(name = "companyId") @ExistCompany Long companyId, @RequestBody @Valid ClientCompanyRequestDto.CreateClientCompanyDto request, @AuthUser Member member) {
        // todo : 본인의 회사에만 등록할 수 있도록 예외처리
        ClientCompany clientCompany = clientCompanyService.create(request, companyId);
        return ResponseEntity.ok(ClientCompanyConverter.toCreateClientCompany(clientCompany));
    }

    @Operation(summary = "[003_03_1]", description = "고객사 수정")
    @Parameters({
            @Parameter(name = "member", hidden = true)
    })
    @PatchMapping("/company/client/{clientId}")
    public ResponseEntity<ClientCompanyResponseDto.UpdateClientCompany> updateClientCompany(@PathVariable(name = "clientId") @ExistClientCompany Long clientCompanyId, @RequestBody @Valid ClientCompanyRequestDto.UpdateClientCompanyDto request, @AuthUser Member member) {
        // todo : 본인 회사의 고객사만 수정할 수 있도록 예외처리
        ClientCompany clientCompany = clientCompanyService.update(clientCompanyId, request, member);
        return ResponseEntity.ok(ClientCompanyConverter.toUpdateClientCompany(clientCompany));
    }

}
