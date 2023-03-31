package cmc.hana.umuljeong.repository;

import cmc.hana.umuljeong.domain.ClientCompany;
import cmc.hana.umuljeong.domain.Company;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ClientCompanyRepository extends JpaRepository<ClientCompany, Long> {
    List<ClientCompany> findByCompany(Company company);

    /*
        @Modifying
    @Query("update ReviewSummary r SET r.likeCnt = r.likeCnt + 1 where r.review = :review")
    int increaseLikeCntByReview(@Param(value = "review") Review review);

    @Modifying
    @Query("update ReviewSummary r SET r.likeCnt = r.likeCnt - 1 where r.review = :review")
    int decreaseLikeCntByReview(@Param(value = "review") Review review);
    */

    @EntityGraph(attributePaths = "businessList")
    Optional<ClientCompany> findById(Long aLong);

    @Modifying
    @Query("update ClientCompany clientCompany set clientCompany.taskCount = clientCompany.taskCount + 1 where clientCompany = :clientCompany")
    void increaseTaskCount(ClientCompany clientCompany);

    @Modifying
    @Query("update ClientCompany clientCompany set clientCompany.taskCount = clientCompany.taskCount - 1 where clientCompany = :clientCompany")
    void decreaseTaskCount(ClientCompany clientCompany);

    @Modifying
    @Query("update ClientCompany clientCompany set clientCompany.businessCount = clientCompany.businessCount + 1 where clientCompany = :clientCompany")
    void increaseBusinessCount(ClientCompany clientCompany);

    @Modifying
    @Query("update ClientCompany clientCompany set clientCompany.businessCount = clientCompany.businessCount - 1 where clientCompany = :clientCompany")
    void decreaseBusinessCount(ClientCompany clientCompany);
}