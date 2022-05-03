package io.github.daniil547.card.elements.attachment;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

public interface FileSystemFileRepository {
    UUID save(MultipartFile file);

    Optional<Path> findById(UUID id);

    void update(MultipartFile file, UUID id);

    void deleteById(UUID id);
}
