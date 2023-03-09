package cmc.hana.umuljeong.service.impl;

import cmc.hana.umuljeong.converter.TaskConverter;
import cmc.hana.umuljeong.domain.Business;
import cmc.hana.umuljeong.domain.Company;
import cmc.hana.umuljeong.domain.Member;
import cmc.hana.umuljeong.domain.Task;
import cmc.hana.umuljeong.repository.BusinessRepository;
import cmc.hana.umuljeong.repository.CompanyRepository;
import cmc.hana.umuljeong.repository.TaskRepository;
import cmc.hana.umuljeong.service.TaskService;
import cmc.hana.umuljeong.web.dto.TaskRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final BusinessRepository businessRepository;
    private final CompanyRepository companyRepository;

    @Transactional
    @Override
    public Task create(TaskRequestDto.CreateTaskDto request, Member member) {
        Task task = TaskConverter.toTask(request, member);
        // 방문건수랑 무슨건수 업데이트 쳐주기
        return taskRepository.save(task);
    }

    @Transactional
    @Override
    public Task update(TaskRequestDto.UpdateTaskDto request) {
        return null;
    }

    @Override
    public List<Task> findByMemberAndDate(Member member, LocalDate date) {
        return taskRepository.findByMemberAndDate(member, date);
    }

    @Override
    public List<Task> findByBusinessAndMemberAndDate(Long businessId, Member member, LocalDate date) {
        Business business = businessRepository.findById(businessId).get();
        return taskRepository.findByBusinessAndMemberAndDate(business, member, date);
    }

    @Override
    public List<Task> findByCompanyAndDate(Long companyId, LocalDate date) {
        Company company = companyRepository.findById(companyId).get();
        return taskRepository.findByCompanyAndDate(company, date);
    }

    @Override
    public Task findById(Long taskId) {
        return taskRepository.findById(taskId).get();
    }
}
