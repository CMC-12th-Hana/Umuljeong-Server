package cmc.hana.umuljeong.repository;

import cmc.hana.umuljeong.domain.TaskImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskImageRepository extends JpaRepository<TaskImage, Long> {
}