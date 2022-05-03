package io.github.daniil547.card.elements.attachment;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "file-storage",
                         ignoreUnknownFields = false)
@Setter
@Getter
public class FileStorageConfiguration {
    private FileStorageType fileStorageType;
    /**
     * <p>This configuration property is meant to be set only once.
     * Reassigning it to the new directory will cause default implementation
     * ({@link StaticDirectoryFileSystemFileRepository})
     * to lose files that were already persisted to the previous directory.</p>
     * <p>For such use cases a separate handler or {@link io.github.daniil547.card.elements.attachment.FileSystemFileRepository}
     * implementation must be written.</p>
     */
    private String fileSystemDirectory;
}
