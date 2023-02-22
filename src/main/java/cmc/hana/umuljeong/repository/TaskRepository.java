package cmc.hana.umuljeong.repository;

import cmc.hana.umuljeong.domain.Business;
import cmc.hana.umuljeong.domain.Member;
import cmc.hana.umuljeong.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByMemberAndDate(Member member, LocalDate date);

    List<Task> findByBusinessAndMemberAndDate(Business business, Member member, LocalDate date);
}