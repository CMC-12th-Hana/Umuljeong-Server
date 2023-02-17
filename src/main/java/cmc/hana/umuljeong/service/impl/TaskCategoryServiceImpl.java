package cmc.hana.umuljeong.service.impl;

import cmc.hana.umuljeong.converter.TaskCategoryConverter;
import cmc.hana.umuljeong.domain.Company;
import cmc.hana.umuljeong.domain.Member;
import cmc.hana.umuljeong.domain.TaskCategory;
import cmc.hana.umuljeong.repository.TaskCategoryRepository;
import cmc.hana.umuljeong.service.TaskCategoryService;
import cmc.hana.umuljeong.web.dto.TaskCategoryRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TaskCategoryServiceImpl implements TaskCategoryService {

    private final TaskCategoryRepository taskCategoryRepository;

    @Transactional
    @Override
    public TaskCategory create(Company company, TaskCategoryRequestDto.CreateTaskCategoryDto request) {
        TaskCategory taskCategory = TaskCategoryConverter.toTaskCategory(company, request);
        return taskCategoryRepository.save(taskCategory);
    }

    @Override
    public List<TaskCategory> updateList(TaskCategoryRequestDto.UpdateTaskCategoryListDto request) {
        return null;
    }

    @Override
    public void deleteList(TaskCategoryRequestDto.DeleteTaskCategoryListDto request) {
        return ;
    }

    @Override
    public List<TaskCategory> findByCompany(Company company) {
        return taskCategoryRepository.findByCompany(company);
    }
}
