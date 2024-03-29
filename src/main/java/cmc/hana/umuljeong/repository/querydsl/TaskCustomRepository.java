package cmc.hana.umuljeong.repository.querydsl;

import cmc.hana.umuljeong.domain.*;

import java.time.LocalDate;
import java.util.List;

public interface TaskCustomRepository {

    List<Task> findByBusinessAndDateAndTaskCategory(Business business, LocalDate date, TaskCategory taskCategory);

    List<Task> findByBusinessAndYearAndMonthAndTaskCategory(Business business, Integer year, Integer month, TaskCategory taskCategory);

    List<Task> findByMemberAndDate(Company company, Member member, LocalDate date);
}
