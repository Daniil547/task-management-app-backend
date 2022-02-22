package io.github.daniil547.user.member;

import io.github.daniil547.common.services.DomainConverter;
import org.springframework.stereotype.Component;

@Component
public class BoardMemberConverter extends DomainConverter<MemberDto, BoardMember> {

    @Override
    protected BoardMember transferEntitySpecificFieldsFromDto(MemberDto dto) {
        BoardMember boardMember = new BoardMember();

        boardMember.setUserId(dto.getUserId());
        boardMember.setBoardId(dto.getPlaceId());
        boardMember.setRole(dto.getRole());

        return boardMember;
    }

    @Override
    protected MemberDto transferDtoSpecificFieldsFromEntity(BoardMember entity) {
        MemberDto memberDto = new MemberDto();

        memberDto.setUserId(entity.getUserId());
        memberDto.setPlaceId(entity.getBoardId());
        memberDto.setRole(entity.getRole());

        return memberDto;
    }
}