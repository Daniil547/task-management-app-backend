package io.github.daniil547.card.elements.attachment;

import io.github.daniil547.common.repositories.DomainRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AttachmentRepository extends DomainRepository<Attachment> {
    @Query("SELECT a.fileStorageType, a.name FROM Attachment a WHERE a.fileContentId = ?1")
    Pair<FileStorageType, String> getFileStorageTypeAndFileName(UUID fileId);

    @Query("SELECT a.fileStorageType FROM Attachment a WHERE a.fileContentId = ?1")
    FileStorageType getFileStorageType(UUID fileId);
}
