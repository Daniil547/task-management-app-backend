package io.github.daniil547.card.elements.attachment;

import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;
import java.util.UUID;

@NoRepositoryBean
public interface FileRepository {
    UUID save(File file);

    Optional<File> findById(UUID id);

    void update(File file);

    void deleteById(UUID id);
}
