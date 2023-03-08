package cmc.hana.umuljeong.service;

import cmc.hana.umuljeong.domain.enums.VerifyMessageStatus;
import cmc.hana.umuljeong.web.dto.AuthRequestDto;

public interface MessageService {
    void sendMessage(String toNumber);

    VerifyMessageStatus verifyMessage(AuthRequestDto.VerifyMessageDto request);
}
