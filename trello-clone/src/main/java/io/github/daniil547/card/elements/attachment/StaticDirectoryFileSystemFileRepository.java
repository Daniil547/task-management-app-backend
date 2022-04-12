package io.github.daniil547.card.elements.attachment;

import io.github.daniil547.common.exceptions.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

import static java.nio.file.StandardOpenOption.CREATE_NEW;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import static java.nio.file.StandardOpenOption.WRITE;

@Component
@AllArgsConstructor(onConstructor_ = @Autowired)
public class StaticDirectoryFileSystemFileRepository implements FileSystemFileRepository {
    private final FileStorageConfiguration storageConfig;


    @Override
    public UUID save(File file) {
        UUID id = UUID.randomUUID();
        file.setId(id); // ID is usually set by the DB, but here we have to do it ourselves

        try {
            Path destination = Path.of(storageConfig.getFileSystemDirectory(), id.toString()); // file's ID is its name
            Files.write(destination, file.getContent(), CREATE_NEW, WRITE);
        } catch (IOException e) {
            throw new IllegalStateException("IOException while writing user's file's contents to disk", e);
        }

        return id;
    }

    @Override
    public Optional<File> findById(UUID id) {
        Path filePath = Path.of(storageConfig.getFileSystemDirectory(), id.toString());
        if (!Files.exists(filePath)) {
            return Optional.empty();
        }

        try {
            byte[] content = Files.readAllBytes(filePath);

            File file = new File();
            file.setId(id);
            file.setContent(content);

            return Optional.of(file);
        } catch (IOException e) {
            throw new IllegalStateException("IOException while reading content from user's file " + id, e);
        }
    }

    @Override
    public void update(File file) {
        UUID id = file.getId();
        Path filePath = Path.of(storageConfig.getFileSystemDirectory(), id.toString());
        if (!Files.exists(filePath)) {
            throw new EntityNotFoundException(id);
        }

        try {
            Files.write(filePath, file.getContent(), TRUNCATE_EXISTING, WRITE);
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
