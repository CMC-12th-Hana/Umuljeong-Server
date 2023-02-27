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

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

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

    @PostConstruct
    void init() {
        staticMemberRepository = this.memberRepository;
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
                .clientCompany(clientCompany)
                .name(request.getName())
                .businessPeriod(businessPeriod)
                .revenue(request.getRevenue())
                .description(request.getDescription())
                .build();

        business.setBusinessMemberList(toBusinessMemberList(request.getMemberIdList(), business));

        return business;
    }
}
