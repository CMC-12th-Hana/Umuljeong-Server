package cmc.hana.umuljeong.web.controller;

import cmc.hana.umuljeong.web.dto.MemberResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberRestController {

    @GetMapping("/company/members")
    public ResponseEntity<MemberResponseDto.MemberListDto> getMemberList() {
        return null;
    }
}
