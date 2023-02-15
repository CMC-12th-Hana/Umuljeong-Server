package cmc.hana.umuljeong.web.controller;

import cmc.hana.umuljeong.converter.BusinessConverter;
import cmc.hana.umuljeong.domain.Business;
import cmc.hana.umuljeong.service.BusinessService;
import cmc.hana.umuljeong.web.dto.BusinessRequestDto;
import cmc.hana.umuljeong.web.dto.BusinessResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BusinessRestController {

    private final BusinessService businessService;

    @GetMapping("/company/client/business")
    public ResponseEntity<BusinessResponseDto.BusinessDto> getBusiness() {
        return null;
    }

    @GetMapping("/company/client/businesses")
    public ResponseEntity<BusinessResponseDto.BusinessListDto> getBusinessList() {
        return null;
    }

    @PostMapping("/company/client/business")
    public ResponseEntity<BusinessResponseDto.CreateBusinessDto> createBusiness(@RequestBody BusinessRequestDto.CreateBusinessDto request) {
        Business business = businessService.create(request);
        return ResponseEntity.ok(BusinessConverter.toCreateBusinessDto(business));
    }
}
