package cmc.hana.umuljeong.repository;

import cmc.hana.umuljeong.domain.Company;
import cmc.hana.umuljeong.domain.TaskCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskCategoryRepository extends JpaRepository<TaskCategory, Long> {
    List<TaskCategory> findByCompany(Company company);

    @Modifying
    @Query("delete from TaskCategory tc where tc.id in :ids")
    void deleteAllByIdInQuery(@Param("ids") List<Long> ids);
}