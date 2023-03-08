package cmc.hana.umuljeong.repository;

import cmc.hana.umuljeong.domain.VerificationMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationMessageRepository extends JpaRepository<VerificationMessage, Long> {
}
