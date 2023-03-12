package cmc.hana.umuljeong.web.controller;

import cmc.hana.umuljeong.auth.annotation.AuthUser;
import cmc.hana.umuljeong.converter.ClientCompanyConverter;
import cmc.hana.umuljeong.domain.ClientCompany;
import cmc.hana.umuljeong.domain.Member;
import cmc.hana.umuljeong.exception.ClientCompanyException;
import cmc.hana.umuljeong.exception.CompanyException;
import cmc.hana.umuljeong.exception.common.ApiErrorResult;
import cmc.hana.umuljeong.exception.common.ErrorCode;
import cmc.hana.umuljeong.service.ClientCompanyService;
import cmc.hana.umuljeong.validation.annotation.ExistClientCompany;
import cmc.hana.umuljeong.validation.annotation.ExistCompany;
import cmc.hana.umuljeong.web.dto.ClientCompanyRequestDto;
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
            @Parameter(name = "member", hidden = true),
            @Parameter(name = "sort", description = "name | taskCount | businessCount"),
            @Parameter(name = "order", description = "ASC | DESC")
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK : 정상응답", content = @Content(schema = @Schema(implementation = ClientCompanyResponseDto.ClientCompanyListDto.class))),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED : 인증되지 않은 사용자", content = @Content(schema = @Schema(implementation = ApiErrorResult.class))),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN : 본인 회사가 아닌 경우", content = @Content(schema = @Schema(implementation = ApiErrorResult.class))),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND : companyId에 해당하는 회사가 존재하지 않는 경우", content = @Content(schema = @Schema(implementation = ApiErrorResult.class)))
    })
    @GetMapping("/company/{companyId}/clients")
    public ResponseEntity<ClientCompanyResponseDto.ClientCompanyListDto> getClientCompanyList(@PathVariable(name = "companyId") @ExistCompany Long companyId,
                                                                                              @RequestParam(name = "name", required = false) String name,
                                                                                              @RequestParam(name = "sort", required = false, defaultValue = "name") String sort,
                                                                                              @RequestParam(name = "order", required = false, defaultValue = "ASC") String order,
                                                                                              @AuthUser Member member) {
        if(companyId != member.getCompany().getId()) throw new CompanyException(ErrorCode.COMPANY_ACCESS_DENIED);

        List<ClientCompany> clientCompanyList = clientCompanyService.findByCompanyAndName(companyId, name, sort, order);
        return ResponseEntity.ok(ClientCompanyConverter.toClientCompanyListDto(clientCompanyList));
    }

    @Operation(summary = "[003_03]", description = "고객사 조회")
    @Parameters({
            @Parameter(name = "member", hidden = true)
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK : 정상응답", content = @Content(schema = @Schema(implementation = ClientCompanyResponseDto.ClientCompanyDto.class))),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED : 인증되지 않은 사용자", content = @Content(schema = @Schema(implementation = ApiErrorResult.class))),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN : 본인 회사의 고객사가 아닌 경우", content = @Content(schema = @Schema(implementation = ApiErrorResult.class))),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND : clientId에 해당하는 고객사가 존재하지 않는 경우", content = @Content(schema = @Schema(implementation = ApiErrorResult.class)))
    })
    @GetMapping("/company/client/{clientId}")
    public ResponseEntity<ClientCompanyResponseDto.ClientCompanyDto> getClientCompany(@PathVariable(name = "clientId") @ExistClientCompany Long clientCompanyId, @AuthUser Member member) {
        if(!member.getCompany().getClientCompanyList().stream().anyMatch(clientCompany -> clientCompany.getId() == clientCompanyId))
            throw new ClientCompanyException(ErrorCode.CLIENT_COMPANY_ACCESS_DENIED);

        ClientCompany clientCompany = clientCompanyService.findById(clientCompanyId);
        return ResponseEntity.ok(ClientCompanyConverter.toClientCompanyDto(clientCompany));
    }

    @Operation(summary = "[003_02]", description = "고객사 추가")
    @Parameters({
            @Parameter(name = "member", hidden = true)
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK : 정상응답", content = @Content(schema = @Schema(implementation = ClientCompanyResponseDto.CreateClientCompany.class))),
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST : 요청 데이터의 값이 형식에 맞지 않은 경우", content = @Content(schema = @Schema(implementation = ApiErrorResult.class))),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED : 인증되지 않은 사용자", content = @Content(schema = @Schema(implementation = ApiErrorResult.class))),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN : 본인 회사가 아닌 경우", content = @Content(schema = @Schema(implementation = ApiErrorResult.class))),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND : companyId에 해당하는 회사가 존재하지 않는 경우", content = @Content(schema = @Schema(implementation = ApiErrorResult.class)))
    })
    @PostMapping("/company/{companyId}/client")
    public ResponseEntity<ClientCompanyResponseDto.CreateClientCompany> createClientCompany(@PathVariable(name = "companyId") @ExistCompany Long companyId, @RequestBody @Valid ClientCompanyRequestDto.CreateClientCompanyDto request, @AuthUser Member member) {
        if(companyId != member.getCompany().getId()) throw new CompanyException(ErrorCode.COMPANY_ACCESS_DENIED);

        ClientCompany clientCompany = clientCompanyService.create(request, companyId);
        return ResponseEntity.ok(ClientCompanyConverter.toCreateClientCompany(clientCompany));
    }

    @Operation(summary = "[003_03_1]", description = "고객사 수정")
    @Parameters({
            @Parameter(name = "member", hidden = true)
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK : 정상응답", content = @Content(schema = @Schema(implementation = ClientCompanyResponseDto.UpdateClientCompany.class))),
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST : 요청 데이터의 값이 형식에 맞지 않은 경우", content = @Content(schema = @Schema(implementation = ApiErrorResult.class))),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED : 인증되지 않은 사용자", content = @Content(schema = @Schema(implementation = ApiErrorResult.class))),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN : 본인 회사의 고객사가 아닌 경우", content = @Content(schema = @Schema(implementation = ApiErrorResult.class))),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND : clientId에 해당하는 고객사가 존재하지 않는 경우", content = @Content(schema = @Schema(implementation = ApiErrorResult.class)))
    })
    @PatchMapping("/company/client/{clientId}")
    public ResponseEntity<ClientCompanyResponseDto.UpdateClientCompany> updateClientCompany(@PathVariable(name = "clientId") @ExistClientCompany Long clientCompanyId, @RequestBody @Valid ClientCompanyRequestDto.UpdateClientCompanyDto request, @AuthUser Member member) {
        if(!member.getCompany().getClientCompanyList().stream().anyMatch(clientCompany -> clientCompany.getId() == clientCompanyId))
            throw new ClientCompanyException(ErrorCode.CLIENT_COMPANY_ACCESS_DENIED);

        ClientCompany clientCompany = clientCompanyService.update(clientCompanyId, request, member);
        return ResponseEntity.ok(ClientCompanyConverter.toUpdateClientCompany(clientCompany));
    }

    @Operation(summary = "[]", description = "고객사 삭제")
    @Parameters({
            @Parameter(name = "member", hidden = true)
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK : 정상응답", content = @Content(schema = @Schema(implementation = ClientCompanyResponseDto.DeleteClientCompany.class))),
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST : 요청 데이터의 값이 형식에 맞지 않은 경우", content = @Content(schema = @Schema(implementation = ApiErrorResult.class))),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED : 인증되지 않은 사용자", content = @Content(schema = @Schema(implementation = ApiErrorResult.class))),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN : 본인 회사의 고객사가 아닌 경우", content = @Content(schema = @Schema(implementation = ApiErrorResult.class))),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND : clientId에 해당하는 고객사가 존재하지 않는 경우", content = @Content(schema = @Schema(implementation = ApiErrorResult.class)))
    })
    @DeleteMapping("/company/client/{clientId}")
    public ResponseEntity<ClientCompanyResponseDto.DeleteClientCompany> deleteClientCompany(@PathVariable(name = "clientId") @ExistClientCompany Long clientCompanyId, @AuthUser Member member) {
        if(!member.getCompany().getClientCompanyList().stream().anyMatch(clientCompany -> clientCompany.getId() == clientCompanyId))
            throw new ClientCompanyException(ErrorCode.CLIENT_COMPANY_ACCESS_DENIED);

        clientCompanyService.delete(clientCompanyId);
        return ResponseEntity.ok(ClientCompanyConverter.toDeleteClientCompany());
    }
}
