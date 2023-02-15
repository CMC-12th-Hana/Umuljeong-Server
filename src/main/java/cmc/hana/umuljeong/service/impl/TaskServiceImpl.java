package cmc.hana.umuljeong.service.impl;

import cmc.hana.umuljeong.domain.Task;
import cmc.hana.umuljeong.repository.TaskRepository;
import cmc.hana.umuljeong.service.TaskService;
import cmc.hana.umuljeong.web.dto.TaskRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Transactional
    @Override
    public Task create(TaskRequestDto.CreateTaskDto request) {
        return null;
    }
}
