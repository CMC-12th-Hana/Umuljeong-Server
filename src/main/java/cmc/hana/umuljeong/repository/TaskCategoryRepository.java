package cmc.hana.umuljeong.repository;

import cmc.hana.umuljeong.domain.TaskCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskCategoryRepository extends JpaRepository<TaskCategory, Long> {
}