package cmc.hana.umuljeong.converter;

import cmc.hana.umuljeong.domain.Business;
import cmc.hana.umuljeong.domain.ClientCompany;
import cmc.hana.umuljeong.domain.Member;
import cmc.hana.umuljeong.domain.embedded.BusinessPeriod;
import cmc.hana.umuljeong.domain.mapping.BusinessMember;
import cmc.hana.umuljeong.repository.MemberRepository;
import cmc.hana.umuljeong.web.dto.BusinessRequestDto;
import cmc.hana.umuljeong.web.dto.BusinessResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BusinessConverter {

    private final MemberRepository memberRepository;

    private static MemberRepository staticMemberRepository;

    public static BusinessResponseDto.UpdateBusinessDto toUpdateBusinessDto(Business business) {
        return BusinessResponseDto.UpdateBusinessDto.builder()
                .businessId(business.getId())
                .updatedAt(business.getUpdatedAt())
                .build();
    }

    public static Business toEtcBusiness(ClientCompany clientCompany) {
        Business business = Business.builder()
                .businessPeriod(BusinessPeriod.builder()
                                .start(LocalDate.of(2000, 1, 1))
                                .finish(LocalDate.of(2000, 1, 1))
                                .build())
                .businessMemberList(new ArrayList<>())
                .revenue(0)
                .description("사업 분류 없음")
                .name("기타")
                .revenue(0)
                .build();
        business.setClientCompany(clientCompany);
        return business;
    }

    public static BusinessResponseDto.DeleteBusinessDto DeleteBusinessDto() {
        return BusinessResponseDto.DeleteBusinessDto.builder()
                .deletedAt(LocalDateTime.now())
                .build();
    }

    @PostConstruct
    void init() {
        this.staticMemberRepository = this.memberRepository;
    }

    public static BusinessResponseDto.CreateBusinessDto toCreateBusinessDto(Business business) {
        return BusinessResponseDto.CreateBusinessDto.builder()
                .businessId(business.getId())
                .createdAt(business.getCreatedAt())
                .build();
    }

    public static BusinessPeriod toBusinessPeriod(BusinessRequestDto.BusinessPeriodDto businessPeriodDto) {
        return BusinessPeriod.builder()
                .start(businessPeriodDto.getStart())
                .finish(businessPeriodDto.getFinish())
                .build();
    }

    private static BusinessResponseDto.BusinessPeriodDto toBusinessPeriodDto(BusinessPeriod businessPeriod) {
        return BusinessResponseDto.BusinessPeriodDto.builder()
                .start(businessPeriod.getStart())
                .finish(businessPeriod.getFinish())
                .build();
    }

    private static BusinessResponseDto.MemberDto toMemberDto(BusinessMember businessMember) {
        return BusinessResponseDto.MemberDto.builder()
                .id(businessMember.getMember().getId())
                .name(businessMember.getMember().getName())
                .build();
    }

    private static List<BusinessResponseDto.MemberDto> toMemberDtoList(List<BusinessMember> businessMemberList) {
        return businessMemberList.stream()
                .map(businessMember -> toMemberDto(businessMember))
                .collect(Collectors.toList());
    }

    public static BusinessResponseDto.BusinessDto toBusinessDto(Business business) {
        return BusinessResponseDto.BusinessDto.
                builder()
                .businessId(business.getId())
                .clientName(business.getClientCompany().getName())
                .name(business.getName())
                .businessPeriodDto(toBusinessPeriodDto(business.getBusinessPeriod()))
                .memberDtoList(toMemberDtoList(business.getBusinessMemberList()))
                .revenue(business.getRevenue())
                .description(business.getDescription())
                .build();
    }

    private static List<BusinessResponseDto.BusinessDto> toBusinessDtoList(List<Business> businessList) {
        return businessList.stream()
                .map(business -> toBusinessDto(business))
                .collect(Collectors.toList());
    }

    public static BusinessResponseDto.BusinessListDto toBusinessListDto(List<Business> businessList) {
        return BusinessResponseDto.BusinessListDto.builder()
                .businessDtoList(toBusinessDtoList(businessList))
                .build();
    }

    private static List<BusinessMember> toBusinessMemberList(List<Long> memberIdList, Business business) {
        return memberIdList.stream()
                .map(memberId -> {
                    Member member = staticMemberRepository.findById(memberId).get();
                    BusinessMember businessMember = BusinessMember.builder().build();
                    businessMember.setMember(member);
                    businessMember.setBusiness(business);
                    return businessMember;
                })
                .collect(Collectors.toList());
    }

    public static Business toBusiness(ClientCompany clientCompany, BusinessRequestDto.CreateBusinessDto request) {
        BusinessPeriod businessPeriod = BusinessPeriod.builder()
                .start(request.getBusinessPeriodDto().getStart())
                .finish(request.getBusinessPeriodDto().getFinish())
                .build();

        Business business = Business.builder()
                .name(request.getName())
                .businessPeriod(businessPeriod)
                .businessMemberList(new ArrayList<>())
                .revenue(request.getRevenue())
                .description(request.getDescription())
                .build();

        business.setClientCompany(clientCompany);

        business.setBusinessMemberList(toBusinessMemberList(request.getMemberIdList(), business));

        return business;
    }
}
