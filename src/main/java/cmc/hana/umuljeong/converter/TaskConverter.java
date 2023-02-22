package cmc.hana.umuljeong.converter;

import cmc.hana.umuljeong.domain.Member;
import cmc.hana.umuljeong.domain.Task;
import cmc.hana.umuljeong.domain.TaskCategory;
import cmc.hana.umuljeong.web.dto.TaskResponseDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TaskConverter {

    public static TaskResponseDto.CreateTaskDto toCreateTaskDto(Task task) {
        return TaskResponseDto.CreateTaskDto.builder()
                .taskId(task.getId())
                .createdAt(task.getCreatedAt())
                .build();
    }

    public static TaskResponseDto.TaskDto toTaskDto(Task task) {
        return TaskResponseDto.TaskDto.builder()
                .taskId(task.getId())
                .taskCategory(task.getTaskCategory().getName())
                .clientName(task.getBusiness().getClientCompany().getName())
                .businessName(task.getBusiness().getName())
                .date(task.getDate())
                .build();
    }

    public static List<TaskResponseDto.TaskDto> toTaskDtoList(List<Task> taskList) {
        return taskList.stream()
                .map(task -> toTaskDto(task))
                .collect(Collectors.toList());
    }

    public static TaskResponseDto.StaffTaskListDto toStaffTaskListDto(List<Task> taskList) {
        TaskResponseDto.StaffTaskListDto staffTaskListDto =
                TaskResponseDto.StaffTaskListDto.builder()
                .taskDtoList(toTaskDtoList(taskList))
                .build();
        staffTaskListDto.setCount(taskList.size());
        return staffTaskListDto;
    }

    public static TaskResponseDto.MemberDto toMemberDto(Member member, List<Task> taskList) {
        List<TaskResponseDto.TaskDto> taskDtoList = toTaskDtoList(taskList);
        return TaskResponseDto.MemberDto.builder()
                .name(member.getName())
                .taskDtoList(taskDtoList)
                .count(taskDtoList.size())
                .build();
    }

    public static TaskResponseDto.LeaderTaskListDto toLeaderTaskListDto(List<Task> taskList) {
        HashMap<Member, List<Task>> memberListHashMap = new HashMap<>();
        for(Task task : taskList) {
            if (memberListHashMap.containsKey(task.getMember())) memberListHashMap.get(task.getMember()).add(task);
            else memberListHashMap.put(task.getMember(), List.of(task));
        }

        TaskResponseDto.LeaderTaskListDto leaderTaskListDto =
                TaskResponseDto.LeaderTaskListDto.builder()
                        .memberDtoList(new ArrayList<>())
                        .build();
        leaderTaskListDto.setCount(memberListHashMap.size());

        for (Map.Entry<Member, List<Task>> entry : memberListHashMap.entrySet()) {
            Member member = entry.getKey();
            List<Task> tasks = entry.getValue();
            leaderTaskListDto.getMemberDtoList().add(toMemberDto(member, tasks));
        }

        return leaderTaskListDto;
    }
}
