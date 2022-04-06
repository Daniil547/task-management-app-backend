package io.github.daniil547.common.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@Data
@MappedSuperclass
public abstract class Domain {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;
}
