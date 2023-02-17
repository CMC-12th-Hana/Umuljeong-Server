package cmc.hana.umuljeong.repository;

import cmc.hana.umuljeong.domain.Company;
import cmc.hana.umuljeong.domain.TaskCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskCategoryRepository extends JpaRepository<TaskCategory, Long> {
    List<TaskCategory> findByCompany(Company company);
}