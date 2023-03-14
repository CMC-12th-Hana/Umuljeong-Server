package cmc.hana.umuljeong.service.impl;

import cmc.hana.umuljeong.converter.TaskConverter;
import cmc.hana.umuljeong.domain.*;
import cmc.hana.umuljeong.repository.*;
import cmc.hana.umuljeong.repository.querydsl.TaskCustomRepository;
import cmc.hana.umuljeong.repository.querydsl.TaskImageCustomRepository;
import cmc.hana.umuljeong.service.TaskService;
import cmc.hana.umuljeong.web.dto.TaskRequestDto;
import kotlin.Pair;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskCustomRepository taskCustomRepository;
    private final BusinessRepository businessRepository;
    private final CompanyRepository companyRepository;
    private final TaskCategoryRepository taskCategoryRepository;
    private final TaskImageProcess taskImageProcess;
    private final TaskImageRepository taskImageRepository;
    private final TaskImageCustomRepository taskImageCustomRepository;

    private final ClientCompanyRepository clientCompanyRepository;


    @Transactional
    @Override
    public Task create(TaskRequestDto.CreateTaskDto request, Member member) {
        Task task = TaskConverter.toTask(request, member);
        ClientCompany clientCompany = task.getBusiness().getClientCompany();
        clientCompanyRepository.increaseTaskCount(clientCompany);

        return taskRepository.save(task);
    }

    @Transactional
    @Override
    public Task update(Long taskId, TaskRequestDto.UpdateTaskDto request) {
        Task task = taskRepository.findById(taskId).get();
        Business business = businessRepository.findById(request.getBusinessId()).get();
        TaskCategory taskCategory = taskCategoryRepository.findById(request.getTaskCategoryId()).get();

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setBusiness(business);
        task.setTaskCategory(taskCategory);

        // Delete Images
        if(request.getDeleteImageIdList() != null) {
            List<TaskImage> taskImageList = taskImageCustomRepository.findByIds(List.of(request.getDeleteImageIdList()));
            System.out.println("SIZE : " + taskImageList.size());
            for(TaskImage taskImage : taskImageList) {
                taskImageProcess.deleteImage(taskImage.getUrl());
                taskImage.removeRelationship();
                taskImageRepository.delete(taskImage);
            }
        }


        // Add Images
        if(request.getAddTaskImageList() != null) {
            List<MultipartFile> taskImageDtoList = request.getAddTaskImageList();
            if(!taskImageDtoList.isEmpty()) {
                System.out.println("ADD IMAGE START!");
                TaskConverter.createAndMapTaskImage(taskImageDtoList, task);
            }
        }

        return task;
    }

    @Override
    public Map<String, Pair<String, Integer>> getStatistic(Company company, Long clientCompanyId) {
        Map<String, Pair<String, Integer>> statisticMap = new HashMap<>();
        List<TaskCategory> taskCategoryList = taskCategoryRepository.findByCompany(company);

        taskCategoryList.stream().forEach(taskCategory -> {
            statisticMap.put(taskCategory.getName(), new Pair<>(taskCategory.getColor(), taskRepository.countByTaskCategoryAndClientCompany_Id(taskCategory, clientCompanyId)));
        });

        return statisticMap;
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

    @Override
    public List<Task> findByBusinessAndDateAndTaskCategory(Long businessId, Integer year, Integer month, Integer day, Long categoryId) {
        Business business = businessRepository.findById(businessId).get();
        TaskCategory taskCategory = null;
        if(categoryId != null) taskCategory = taskCategoryRepository.findById(categoryId).get();

        List<Task> taskList;

        if(day == null) {
            taskList = taskCustomRepository.findByBusinessAndYearAndMonthAndTaskCategory(business, year, month, taskCategory);
        }
        else {
            LocalDate date = LocalDate.of(year, month, day);
            taskList = taskCustomRepository.findByBusinessAndDateAndTaskCategory(business, date, taskCategory);
        }

        return taskList;
    }

    @Transactional
    @Override
    public void delete(Long taskId) {
        Task task = taskRepository.findById(taskId).get();

        ClientCompany clientCompany = task.getBusiness().getClientCompany();
        clientCompanyRepository.decreaseTaskCount(clientCompany);

        List<TaskImage> taskImageList = task.getTaskImageList();
        for(TaskImage taskImage : taskImageList) {
            taskImageProcess.deleteImage(taskImage.getUrl());
        }

        task.removeRelationship();
        taskRepository.delete(task);
        return ;
    }
}
