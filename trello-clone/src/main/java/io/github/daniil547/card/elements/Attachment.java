package io.github.daniil547.card.elements;

import io.github.daniil547.common.domain.Domain;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

//TODO figure out how to transfer files over the network and implement this

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data

@Entity
@Table(name = "attachments")
public class Attachment extends Domain {
    @Column
    private String name;
    @Column
    private String filePath;

    @Column
    private UUID cardId;
}
