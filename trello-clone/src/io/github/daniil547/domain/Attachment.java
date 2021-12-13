package io.github.daniil547.domain;

import lombok.Data;

@Data
public class Attachment extends Domain implements MonoCardElement<String> {
    private String filePath;

    /*TODO: implement*/
}
