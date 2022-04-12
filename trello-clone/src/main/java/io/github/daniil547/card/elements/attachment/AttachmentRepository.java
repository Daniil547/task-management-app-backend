package io.github.daniil547.card.elements.attachment;

import io.github.daniil547.common.repositories.DomainRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface AttachmentRepository extends DomainRepository<Attachment> {
    @Query("SELECT a.fileStoragePlace FROM Attachment a WHERE a.fileContentId = ?1")
    FileStoragePlace getFileStoragePlace(UUID fileId);
}
