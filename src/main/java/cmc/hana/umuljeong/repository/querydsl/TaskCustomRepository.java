package cmc.hana.umuljeong.repository.querydsl;

import cmc.hana.umuljeong.domain.Business;
import cmc.hana.umuljeong.domain.Member;
import cmc.hana.umuljeong.domain.Task;
import cmc.hana.umuljeong.domain.TaskCategory;

import java.time.LocalDate;
import java.util.List;

public interface TaskCustomRepository {

    List<Task> findByBusinessAndDateAndTaskCategory(Business business, LocalDate date, TaskCategory taskCategory);

    List<Task> findByBusinessAndYearAndMonthAndTaskCategory(Business business, Integer year, Integer month, TaskCategory taskCategory);

    List<Task> findByMemberAndDate(Member member, LocalDate date);
}
