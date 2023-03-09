package cmc.hana.umuljeong.repository;

import cmc.hana.umuljeong.domain.common.Uuid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UuidRepository extends JpaRepository<Uuid, Long> {
}
