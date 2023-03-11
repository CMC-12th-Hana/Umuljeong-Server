package cmc.hana.umuljeong.repository;

import cmc.hana.umuljeong.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> { // todo : 검색 쿼리 QueryDsl로 바꾸기
    List<Task> findByMemberAndDate(Member member, LocalDate date);

    List<Task> findByBusinessAndMemberAndDate(Business business, Member member, LocalDate date);

    @Query("select task from Task task where task.business.clientCompany.company = :company and task.date = :date")
    List<Task> findByCompanyAndDate(@Param(value = "company") Company company, @Param(value = "date") LocalDate date);

    @Query("select count(task) from Task task where task.taskCategory = :taskCategory and task.business.clientCompany.id = :clientCompanyId")
    Integer countByTaskCategoryAndClientCompany_Id(TaskCategory taskCategory, Long clientCompanyId);


    @Query("select task from Task task where task.business = :business and task.date = :date and task.taskCategory = :taskCategory")
    List<Task> findByBusinessAndDateAndTaskCategory(Business business, LocalDate date, TaskCategory taskCategory);

    @Query("select task from Task task where task.business = :business and task.date = :date")
    List<Task> findByBusinessAndDate(Business business, LocalDate date);

    @Query("select task from Task task where task.business = :business and year(task.date) = :year and month(task.date) = :month and task.taskCategory = :taskCategory")
    List<Task> findByBusinessAndYearAndMonthAndTaskCategory(Business business, Integer year, Integer month, TaskCategory taskCategory);

    @Query("select task from Task task where task.business = :business and year(task.date) = :year and month(task.date) = :month")
    List<Task> findByBusinessAndYearAndMonth(Business business, Integer year, Integer month);
}