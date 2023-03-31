package cmc.hana.umuljeong.repository;

import cmc.hana.umuljeong.domain.*;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> { // todo : 검색 쿼리 QueryDsl로 바꾸기


    @EntityGraph(attributePaths = "taskImageList")
    Optional<Task> findById(Long aLong);

    List<Task> findByMemberAndDate(Member member, LocalDate date);

    @EntityGraph(attributePaths = "taskImageList")
    List<Task> findByBusinessAndMemberAndDate(Business business, Member member, LocalDate date);

    @EntityGraph(attributePaths = "taskImageList")
    @Query("select task from Task task where task.business.clientCompany.company = :company and task.date = :date")
    List<Task> findByCompanyAndDate(@Param(value = "company") Company company, @Param(value = "date") LocalDate date);

    @Query("select count(task) from Task task where task.taskCategory = :taskCategory and task.business.clientCompany.id = :clientCompanyId")
    Integer countByTaskCategoryAndClientCompany_Id(TaskCategory taskCategory, Long clientCompanyId);

    @Query("select count(task) from Task task where task.taskCategory = :taskCategory and task.business.id = :businessId")
    Integer countByTaskCategoryAndBusiness_Id(TaskCategory taskCategory, Long businessId);

    @EntityGraph(attributePaths = "taskImageList")
    List<Task> findByTaskCategory(TaskCategory taskCategory);
}