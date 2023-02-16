package cmc.hana.umuljeong.service.impl;

import cmc.hana.umuljeong.domain.Member;
import cmc.hana.umuljeong.domain.Task;
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

    @Transactional
    @Override
    public Task create(TaskRequestDto.CreateTaskDto request) {
        return null;
    }

    @Override
    public List<Task> findByMemberAndDate(Member mockMember, LocalDate date) {
        return null;
    }
}
