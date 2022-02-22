package io.github.daniil547.user.member;

import io.github.daniil547.common.services.DomainConverter;
import org.springframework.stereotype.Component;

@Component
public class WorkspaceMemberConverter extends DomainConverter<MemberDto, WorkspaceMember> {

    @Override
    protected WorkspaceMember transferEntitySpecificFieldsFromDto(MemberDto dto) {
        WorkspaceMember workspaceMember = new WorkspaceMember();

        workspaceMember.setUserId(dto.getUserId());
        workspaceMember.setWorkspaceId(dto.getPlaceId());
        workspaceMember.setRole(dto.getRole());

        return workspaceMember;
    }

    @Override
    protected MemberDto transferDtoSpecificFieldsFromEntity(WorkspaceMember entity) {
        MemberDto memberDto = new MemberDto();

        memberDto.setUserId(entity.getUserId());
        memberDto.setPlaceId(entity.getWorkspaceId());
        memberDto.setRole(entity.getRole());

        return memberDto;
    }
}