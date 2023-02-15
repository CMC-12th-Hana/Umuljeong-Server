package cmc.hana.umuljeong.service;

import cmc.hana.umuljeong.domain.Task;
import cmc.hana.umuljeong.web.dto.TaskRequestDto;

public interface TaskService {
    Task create(TaskRequestDto.CreateTaskDto request);
}
