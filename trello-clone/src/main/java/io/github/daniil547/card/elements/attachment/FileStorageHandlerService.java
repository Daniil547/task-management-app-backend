package io.github.daniil547.card.elements.attachment;

import io.github.daniil547.common.exceptions.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

@Component
@AllArgsConstructor(onConstructor_ = @Autowired)
public class FileStorageHandlerService {
    private final AttachmentRepository attachmentRepository;
    private final JpaFileRepository jpaFileRepository;
    private final FileSystemFileRepository fileSystemFileRepository;
    private final FileStorageConfiguration configuration;

    public UUID save(MultipartFile mpFile) {


        return switch (configuration.getFileStorageType()) {
            case DATABASE -> {
                File file = new File();
                try {
                    file.setContent(mpFile.getBytes());
                } catch (IOException e) {
                    throw new IllegalStateException("IOException while reading content from MultipartFile", e);
                }
                yield jpaFileRepository.save(file).getId();
            }
            case FILE_SYSTEM -> {
                yield fileSystemFileRepository.save(mpFile);
            }
        };
    }

    public Pair<Resource, String> find(UUID id) {
        Pair<FileStorageType, String> storageAndName = getStorageTypeAndFileName(id);
        Pair<Resource, String> res = switch (storageAndName.getFirst()) {
            case DATABASE -> {
                File file = jpaFileRepository.findById(id)
                                             .orElseThrow(() -> {
                                                 throw new EntityNotFoundException(id);
                                             });
                yield Pair.of(new ByteArrayResource(file.getContent()), storageAndName.getSecond());
            }
            case FILE_SYSTEM -> {
                Path path = fileSystemFileRepository.findById(id)
                                                    .orElseThrow(() -> {
                                                        throw new EntityNotFoundException(id);
                                                    });

                yield Pair.of(new FileSystemResource(path), storageAndName.getSecond());
            }
        };

        return res;
    }

    private Pair<FileStorageType, String> getStorageTypeAndFileName(UUID id) {
        return attachmentRepository.getFileStorageTypeAndFileName(id);
    }

    private FileStorageType getStorageType(UUID id) {
        return attachmentRepository.getFileStorageType(id);
    }

    public void update(MultipartFile mpfile, UUID id) {
        FileStorageType storagePlace = getStorageType(id);
        File file = new File();
        file.setId(id);
        try {
            file.setContent(mpfile.getBytes());
        } catch (IOException e) {
            throw new IllegalStateException("IOException while reading content from MultipartFile", e);
        }

        switch (storagePlace) {
            case DATABASE -> jpaFileRepository.save(file);
            case FILE_SYSTEM -> fileSystemFileRepository.update(mpfile, id);
        }
    }

    public void delete(UUID id) {
        FileStorageType storagePlace = getStorageType(id);
        switch (storagePlace) {
            case DATABASE -> jpaFileRepository.deleteById(id);
            case FILE_SYSTEM -> fileSystemFileRepository.deleteById(id);
        }
    }
}
