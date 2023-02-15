package cmc.hana.umuljeong.service;

import cmc.hana.umuljeong.domain.TaskCategory;
import cmc.hana.umuljeong.web.dto.TaskCategoryRequestDto;

public interface TaskCategoryService {
    TaskCategory create(TaskCategoryRequestDto.CreateTaskCategoryDto request);
}
