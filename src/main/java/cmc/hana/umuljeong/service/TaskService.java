package cmc.hana.umuljeong.service;

import cmc.hana.umuljeong.domain.Member;
import cmc.hana.umuljeong.domain.Task;
import cmc.hana.umuljeong.web.dto.TaskRequestDto;

import java.time.LocalDate;
import java.util.List;

public interface TaskService {
    Task create(TaskRequestDto.CreateTaskDto request);

    List<Task> findByMemberAndDate(Member mockMember, LocalDate date);
}
