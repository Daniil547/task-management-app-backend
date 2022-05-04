package io.github.daniil547.card.elements.attachment;

import io.github.daniil547.common.domain.Domain;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data

@Entity
@Table(name = "files_contents")
public class File extends Domain {
    @Type(type = "org.hibernate.type.BinaryType")
    @Basic(fetch = FetchType.EAGER, //the only column (besides ID) so no point in lazy fetching
           optional = false)
    private byte[] content;


}
