package io.github.daniil547.card.elements.attachment;

import io.github.daniil547.common.domain.Domain;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.util.UUID;

//TODO add multipart

/**
 * Attachment ID serves three purposes:
 * ID in attachment table,
 * ID in file_content table,
 * name of the file when it is stored via file system
 */

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data

@Entity
@Table(name = "attachments")
public class Attachment extends Domain {
    @Column
    private String name;
    @Column
    private String extension;
    @Column
    private UUID fileContentId;
    @Column(columnDefinition = "enum('DATABASE', 'FILE_SYSTEM')")
    @Enumerated(EnumType.STRING)
    private FileStorageType fileStorageType;

    @Column
    private UUID cardId;
}
