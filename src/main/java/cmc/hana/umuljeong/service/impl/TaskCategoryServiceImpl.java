package cmc.hana.umuljeong.service.impl;

import cmc.hana.umuljeong.converter.TaskCategoryConverter;
import cmc.hana.umuljeong.domain.Company;
import cmc.hana.umuljeong.domain.Member;
import cmc.hana.umuljeong.domain.TaskCategory;
import cmc.hana.umuljeong.repository.CompanyRepository;
import cmc.hana.umuljeong.repository.TaskCategoryRepository;
import cmc.hana.umuljeong.service.TaskCategoryService;
import cmc.hana.umuljeong.web.dto.TaskCategoryRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TaskCategoryServiceImpl implements TaskCategoryService {

    private final TaskCategoryRepository taskCategoryRepository;
    private final CompanyRepository companyRepository;

    @Transactional
    @Override
    public TaskCategory create(Long companyId, TaskCategoryRequestDto.CreateTaskCategoryDto request) {
        Company company = companyRepository.findById(companyId).get();
        TaskCategory taskCategory = TaskCategoryConverter.toTaskCategory(company, request);
        return taskCategoryRepository.save(taskCategory);
    }

    @Transactional
    @Override
    public TaskCategory update(Long taskCategoryId, TaskCategoryRequestDto.UpdateTaskCategoryDto updateTaskCategoryDto) {
        TaskCategory taskCategory = taskCategoryRepository.findById(taskCategoryId).get();
        taskCategory.setName(updateTaskCategoryDto.getName());
        taskCategory.setColor(updateTaskCategoryDto.getColor());
        return taskCategory;
    }

//    @Transactional
//    @Override
//    public List<TaskCategory> updateList(Long companyId, TaskCategoryRequestDto.UpdateTaskCategoryListDto request) {
//        return request.getUpdateTaskCategoryDtoList().stream()
//                .map((updateTaskCategoryDto) -> update(updateTaskCategoryDto))
//                .collect(Collectors.toList());
//    }

    @Transactional
    @Override
    public void deleteList(TaskCategoryRequestDto.DeleteTaskCategoryListDto request) {
        taskCategoryRepository.deleteAllByIdInQuery(request.getTaskCategoryIdList());
        return ;
    }

    @Override
    public List<TaskCategory> findByCompany(Long companyId) {
        Company company = companyRepository.findById(companyId).get();
        return taskCategoryRepository.findByCompany(company);
    }
}
