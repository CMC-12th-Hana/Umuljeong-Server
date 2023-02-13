package cmc.hana.umuljeong.repository;

import cmc.hana.umuljeong.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}