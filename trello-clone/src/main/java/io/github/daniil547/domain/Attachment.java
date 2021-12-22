package io.github.daniil547.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class Attachment extends Domain /* implements MonoCardElement<String> */ {
    private String name;
    private String filePath;
    private String type;

    /*TODO: implement*/
}
