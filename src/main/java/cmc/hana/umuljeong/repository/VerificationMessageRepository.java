package cmc.hana.umuljeong.repository;

import cmc.hana.umuljeong.domain.VerificationMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationMessageRepository extends JpaRepository<VerificationMessage, Long> {
    Optional<VerificationMessage> findByPhoneNumber(String phoneNumber);
}
