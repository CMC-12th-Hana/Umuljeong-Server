package cmc.hana.umuljeong.converter;

import cmc.hana.umuljeong.aws.s3.TaskImagePackageMetaData;
import cmc.hana.umuljeong.domain.Member;
import cmc.hana.umuljeong.domain.Task;
import cmc.hana.umuljeong.domain.TaskImage;
import cmc.hana.umuljeong.domain.common.Uuid;
import cmc.hana.umuljeong.repository.BusinessRepository;
import cmc.hana.umuljeong.repository.TaskCategoryRepository;
import cmc.hana.umuljeong.service.impl.TaskImageProcess;
import cmc.hana.umuljeong.web.dto.TaskRequestDto;
import cmc.hana.umuljeong.web.dto.TaskResponseDto;
import kotlin.Pair;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TaskConverter {

    private final BusinessRepository businessRepository;
    private final TaskCategoryRepository taskCategoryRepository;
    private final TaskImageProcess taskImageProcess;

    private static BusinessRepository staticBusinessRepository;
    private static TaskCategoryRepository staticTaskCategoryRepository;
    private static TaskImageProcess staticTaskImageProcess;

    public static TaskResponseDto.TaskStatisticListDto toTaskStatisticListDto(Map<String, Pair<String, Integer>> taskStatistic) {
        List<TaskResponseDto.TaskStatisticDto> taskStatisticDtoList = new ArrayList<>();

        for(Map.Entry<String, Pair<String, Integer>> entry : taskStatistic.entrySet()) {
            String name = entry.getKey(); String color = entry.getValue().component1(); Integer count = entry.getValue().component2();
            taskStatisticDtoList.add(toTaskStatisticDto(name, color, count));
        }

        return TaskResponseDto.TaskStatisticListDto.builder()
                .taskStatisticDtoList(taskStatisticDtoList)
                .build();
    }

    private static TaskResponseDto.TaskStatisticDto toTaskStatisticDto(String name, String color, Integer count) {
        return TaskResponseDto.TaskStatisticDto.builder()
                .name(name)
                .color(color)
                .count(count)
                .build();
    }

    public static TaskResponseDto.DeleteTaskDto toDeleteTaskDto() {
        return TaskResponseDto.DeleteTaskDto.builder()
                .deletedAt(LocalDateTime.now())
                .build();
    }

    @PostConstruct
    void init() {
        this.staticBusinessRepository = this.businessRepository;
        this.staticTaskCategoryRepository = this.taskCategoryRepository;
        this.staticTaskImageProcess = this.taskImageProcess;
    }

    public static TaskResponseDto.CreateTaskDto toCreateTaskDto(Task task) {
        return TaskResponseDto.CreateTaskDto.builder()
                .taskId(task.getId())
                .createdAt(task.getCreatedAt())
                .build();
    }

    public static List<TaskResponseDto.TaskImageDto> toTaskImageDtoList(List<TaskImage> taskImageList) {
        return taskImageList.stream()
                .map(taskImage -> TaskResponseDto.TaskImageDto.builder()
                        .taskImageId(taskImage.getId())
                        .url(taskImage.getUrl()).build()
                )
                .collect(Collectors.toList());
    }

    public static TaskResponseDto.TaskDto toTaskDto(Task task) {
        return TaskResponseDto.TaskDto.builder()
                .taskId(task.getId())
                .memberId(task.getMember().getId())
                .title(task.getTitle())
                .taskCategory(task.getTaskCategory() != null ? task.getTaskCategory().getName() : "미지정")
                .taskCategoryColor(task.getTaskCategory() != null ? task.getTaskCategory().getColor() : "")
                .clientName(task.getBusiness().getClientCompany().getName())
                .businessName(task.getBusiness().getName())
                .description(task.getDescription())
                .date(task.getDate())
                .taskImageDtoList(toTaskImageDtoList(task.getTaskImageList()))
                .build();
    }

    public static List<TaskResponseDto.TaskDto> toTaskDtoList(List<Task> taskList) {
        return taskList.stream()
                .map(task -> toTaskDto(task))
                .collect(Collectors.toList());
    }

    public static TaskResponseDto.TaskListDto toStaffTaskListDto(List<Task> taskList) {
        TaskResponseDto.TaskListDto staffTaskListDto =
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

    public static TaskResponseDto.TaskListDto toLeaderTaskListDto(List<Task> taskList) {
        HashMap<Member, List<Task>> memberListHashMap = new HashMap<>();
        for(Task task : taskList) {
            if (memberListHashMap.containsKey(task.getMember())) memberListHashMap.get(task.getMember()).add(task);
            else {
                List<Task> tasks = new ArrayList<>(); tasks.add(task);
                memberListHashMap.put(task.getMember(), tasks);
            }
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

    public static TaskResponseDto.UpdateTaskDto toUpdateTaskDto(Task task) {
        return TaskResponseDto.UpdateTaskDto.builder()
                .taskId(task.getId())
                .updatedAt(task.getUpdatedAt())
                .build();
    }

    public static Task toTask(TaskRequestDto.CreateTaskDto request, Member member) {
        Task task = Task.builder()
                .taskCategory(staticTaskCategoryRepository.findById(request.getTaskCategoryId()).get())
                .title(request.getTitle())
                .taskImageList(new ArrayList<>())
                .date(request.getDate())
                .description(request.getDescription())
                .build();
        task.setMember(member);
        task.setBusiness(staticBusinessRepository.findById(request.getBusinessId()).get());

        List<MultipartFile> taskImageDtoList = request.getTaskImageList();
        if(taskImageDtoList != null && !taskImageDtoList.isEmpty()) {
               createAndMapTaskImage(taskImageDtoList, task);
        }
        return task;
    }

    public static void createAndMapTaskImage(List<MultipartFile> taskImageDtoList, Task task) {
        List<MultipartFile> imageFileList = taskImageDtoList;

        ExecutorService executorService = Executors.newFixedThreadPool(Math.min(imageFileList.size(), 5));
        List<CompletableFuture<Void>> futures = imageFileList.stream().map((image) -> CompletableFuture.runAsync(() -> {
                    Uuid uuid = staticTaskImageProcess.createUUID();
                    TaskImagePackageMetaData taskImagePackageMeta = TaskImagePackageMetaData.builder()
                            .businessId(task.getBusiness().getId())
                            .uuid(uuid.getUuid())
                            .uuidEntity(uuid)
                            .build();
                    staticTaskImageProcess.uploadImageAndMapToReview(image, taskImagePackageMeta, task);
                }, executorService))
                .collect(Collectors.toList());

        /** blocking **/
        List<Void> blockingList = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenApply(Void -> futures.stream().map(CompletableFuture::join).collect(Collectors.toList()))
                .join();
    }
}
