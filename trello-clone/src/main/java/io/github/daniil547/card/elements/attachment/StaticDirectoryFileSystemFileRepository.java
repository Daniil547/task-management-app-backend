package io.github.daniil547.card.elements.attachment;

import io.github.daniil547.common.exceptions.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

@Component
@AllArgsConstructor(onConstructor_ = @Autowired)
public class StaticDirectoryFileSystemFileRepository implements FileSystemFileRepository {
    private final FileStorageConfiguration storageConfig;


    @Override
    public UUID save(MultipartFile file) {
        // ID is usually set by the DB, but here we have to do it ourselves
        UUID id = UUID.randomUUID();

        try {
            // file's ID is its name
            Path destination = Path.of(storageConfig.getFileSystemDirectory(), id.toString());

            Files.createFile(destination);

            file.transferTo(destination);
        } catch (IOException e) {
            throw new IllegalStateException("IOException while writing user's file's contents to disk", e);
        }

        return id;
    }

    @Override
    public Optional<Path> findById(UUID id) {
        Path filePath = Path.of(storageConfig.getFileSystemDirectory(), id.toString());
        if (!Files.exists(filePath)) {
            return Optional.empty();
        }

        return Optional.of(filePath);
    }

    @Override
    public void update(MultipartFile file, UUID id) {
        Path filePath = Path.of(storageConfig.getFileSystemDirectory(), id.toString());
        if (!Files.exists(filePath)) {
            throw new EntityNotFoundException(id);
        }

        try {
            file.transferTo(filePath);
        } catch (IOException e) {
            throw new IllegalStateException("IOException while writing (updating) user's file's contents to disk", e);
        }
    }

    @Override
    public void deleteById(UUID id) {
        Path filePath = Path.of(storageConfig.getFileSystemDirectory(), id.toString());
        if (!Files.exists(filePath)) {
            throw new EntityNotFoundException(id);
        }

        try {
            Files.delete(filePath);

        } catch (IOException e) {
            throw new IllegalStateException("IOException while writing user's file's contents to disk", e);
        }
    }
}
