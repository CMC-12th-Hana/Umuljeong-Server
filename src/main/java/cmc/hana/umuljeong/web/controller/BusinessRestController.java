package cmc.hana.umuljeong.web.controller;

import cmc.hana.umuljeong.web.dto.BusinessResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BusinessRestController {

    @GetMapping("/company/client/business")
    public ResponseEntity<BusinessResponseDto.BusinessDto> getBusiness() {
        return null;
    }

    @GetMapping("/company/client/businesses")
    public ResponseEntity<BusinessResponseDto.BusinessListDto> getBusinessList() {
        return null;
    }

    @PostMapping("/company/client/business")
    public ResponseEntity<BusinessResponseDto.CreateBusinessDto> createBusiness() {
        return null;
    }
}
