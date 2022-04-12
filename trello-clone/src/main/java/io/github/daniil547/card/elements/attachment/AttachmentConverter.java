package io.github.daniil547.card.elements.attachment;

import io.github.daniil547.common.services.DomainConverter;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor(onConstructor_ = @Autowired)
public class AttachmentConverter extends DomainConverter<AttachmentDto, Attachment> {
    AttachmentRepository attachmentRepository;
    FileStorageConfiguration fileStorageConfig;

    @Override
    protected Attachment transferEntitySpecificFieldsFromDto(AttachmentDto dto) {
        Attachment attachment = new Attachment();
        attachment.setCardId(dto.getCardId());
        attachment.setName(dto.getName());
        attachment.setExtension(dto.getExtension());
        attachment.setFileContentId(dto.getFileContentId());
        if (dto.getId() == null) {
            attachment.setFileStoragePlace(fileStorageConfig.getFileStoragePlace());
        } else {
            FileStoragePlace storagePlace = attachmentRepository.getFileStoragePlace(dto.getId());
            attachment.setFileStoragePlace(storagePlace);
        }

        return attachment;
    }

    @Override
    protected AttachmentDto transferDtoSpecificFieldsFromEntity(Attachment entity) {
        AttachmentDto attachmentDto = new AttachmentDto();
        attachmentDto.setCardId(entity.getCardId());
        attachmentDto.setName(entity.getName());
        attachmentDto.setExtension(entity.getExtension());
        attachmentDto.setFileContentId(entity.getFileContentId());

        return attachmentDto;
    }
}
