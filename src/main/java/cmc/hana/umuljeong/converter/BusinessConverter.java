package cmc.hana.umuljeong.converter;

import cmc.hana.umuljeong.domain.Business;
import cmc.hana.umuljeong.domain.Member;
import cmc.hana.umuljeong.domain.embedded.BusinessPeriod;
import cmc.hana.umuljeong.domain.mapping.BusinessMember;
import cmc.hana.umuljeong.web.dto.BusinessResponseDto;
import org.springframework.security.core.parameters.P;

import java.util.List;
import java.util.stream.Collectors;

public class BusinessConverter {

    public static BusinessResponseDto.CreateBusinessDto toCreateBusinessDto(Business business) {
        return BusinessResponseDto.CreateBusinessDto.builder()
                .businessId(business.getId())
                .createdAt(business.getCreatedAt())
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
}
