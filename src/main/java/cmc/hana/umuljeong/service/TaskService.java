package cmc.hana.umuljeong.service;

import cmc.hana.umuljeong.domain.Company;
import cmc.hana.umuljeong.domain.Member;
import cmc.hana.umuljeong.domain.Task;
import cmc.hana.umuljeong.web.dto.TaskRequestDto;
import kotlin.Pair;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface TaskService {
    Task create(TaskRequestDto.CreateTaskDto request, Member member);

    List<Task> findByMemberAndDate(Member member, LocalDate date);

    List<Task> findByBusinessAndMemberAndDate(Long businessId, Member member, LocalDate date);

    List<Task> findByCompanyAndDate(Long companyId, LocalDate date);

    Task findById(Long taskId);

    Task update(TaskRequestDto.UpdateTaskDto request);

    Map<String, Pair<String, Integer>> getStatistic(Company company, Long clientCompanyId);

    List<Task> findByBusinessAndDateAndTaskCategory(Long businessId, Integer year, Integer month, Integer day, Long categoryId);

    void delete(Long taskId);
}
