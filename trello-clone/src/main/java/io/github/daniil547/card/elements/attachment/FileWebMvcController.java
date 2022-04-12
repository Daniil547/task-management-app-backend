package io.github.daniil547.card.elements.attachment;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/files")
@AllArgsConstructor(onConstructor_ = {@Autowired})
@Api(tags = "File Resource", description = "CRUD endpoint for files of attachments")
public class FileWebMvcController {
    private final FileStorageHandlerService fileStorageHandler;

    @GetMapping("/{id}")
    //@Content(mediaType = )
    public ResponseEntity<File> download(@PathVariable
                                         @ApiParam(name = "id",
                                                   value = "id (uuid) of a file you want to download",
                                                   required = true)
                                                 UUID id) {
        return new ResponseEntity<>(fileStorageHandler.find(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UUID> upload(@RequestParam("file")
                                       @RequestPart("file")
                                       @ApiParam(name = "file",
                                                 value = "file you want to upload",
                                                 required = true,
                                                 type = "file")
                                               MultipartFile file) {
        return new ResponseEntity<>(fileStorageHandler.save(file), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> uploadUpdate(@RequestParam("file")
                                          @RequestPart("file")
                                          @ApiParam(name = "file",
                                                    value = "file you want to upload",
                                                    required = true,
                                                    type = "file")
                                                  MultipartFile file,
                                          @PathVariable
                                          @ApiParam(name = "id",
                                                    value = "id (uuid) of an object you want to get",
                                                    required = true)
                                                  UUID id) {
        fileStorageHandler.update(file, id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")

    public ResponseEntity<?> delete(@PathVariable
                                    @ApiParam(name = "id",
                                              value = "id (uuid) of an object you want to get",
                                              required = true)
                                            UUID id) {
        fileStorageHandler.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
