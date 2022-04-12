package io.github.daniil547.card.elements.attachment;

import io.github.daniil547.common.exceptions.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Component
@AllArgsConstructor(onConstructor_ = @Autowired)
public class FileStorageHandlerService {
    private final AttachmentRepository attachmentRepository;
    private final JpaFileRepository jpaFileRepository;
    private final FileSystemFileRepository fileSystemFileRepository;
    private final FileStorageConfiguration configuration;

    public UUID save(MultipartFile mpFile) {
        File file = new File();
        try {
            file.setContent(mpFile.getBytes());
        } catch (IOException e) {
            throw new IllegalStateException("IOException while reading content from MultipartFile", e);
        }

        return switch (configuration.getFileStoragePlace()) {
            case DATABASE -> jpaFileRepository.save(file).getId();
            case FILE_SYSTEM -> fileSystemFileRepository.save(file);
        };
    }

    public File find(UUID id) {
        FileStoragePlace storagePlace = getStoragePlace(id);
        return switch (storagePlace) {
            case DATABASE -> jpaFileRepository.findById(id)
                                              .orElseThrow(() -> {
                                                  throw new EntityNotFoundException(id);
                                              });
            case FILE_SYSTEM -> fileSystemFileRepository.findById(id)
                                                        .orElseThrow(() -> {
                                                            throw new EntityNotFoundException(id);
                                                        });
        };
    }

    private FileStoragePlace getStoragePlace(UUID id) {
        return attachmentRepository.getFileStoragePlace(id);
    }

    public void update(MultipartFile file, UUID id) {
        FileStoragePlace storagePlace = getStoragePlace(id);
        File fileEntity = new File();
        fileEntity.setId(id);
        try {
            fileEntity.setContent(file.getBytes());
        } catch (IOException e) {
            throw new IllegalStateException("IOException while reading content from MultipartFile", e);
        }

        switch (storagePlace) {
            case DATABASE -> jpaFileRepository.save(fileEntity);
            case FILE_SYSTEM -> fileSystemFileRepository.update(fileEntity);
        }
    }

    public void delete(UUID id) {
        FileStoragePlace storagePlace = getStoragePlace(id);
        switch (storagePlace) {
            case DATABASE -> jpaFileRepository.deleteById(id);
            case FILE_SYSTEM -> fileSystemFileRepository.deleteById(id);
        }
    }
}
