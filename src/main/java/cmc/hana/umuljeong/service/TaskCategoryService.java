package cmc.hana.umuljeong.service;

import cmc.hana.umuljeong.domain.Member;
import cmc.hana.umuljeong.domain.TaskCategory;
import cmc.hana.umuljeong.web.dto.TaskCategoryRequestDto;

import java.util.List;

public interface TaskCategoryService {
    TaskCategory create(TaskCategoryRequestDto.CreateTaskCategoryDto request);

    List<TaskCategory> updateList(TaskCategoryRequestDto.UpdateTaskCategoryListDto request);

    void deleteList(TaskCategoryRequestDto.DeleteTaskCategoryListDto request);

    List<TaskCategory> findByMember(Member mockMember);
}
