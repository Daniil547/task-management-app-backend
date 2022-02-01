package io.github.daniil547.workspace;

import com.fasterxml.jackson.annotation.JsonView;
import io.github.daniil547.common.exceptions.EntityNotFoundException;
import io.github.daniil547.common.util.JsonDtoView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/workspaces")
public class WorkspaceWebMvcController {
    private final WorkspaceService service;

    @Autowired
    public WorkspaceWebMvcController(WorkspaceService service) {
        this.service = service;
    }

    //TODO add pagination after migration to Data JPA
    @GetMapping("/")
    public ResponseEntity<List<WorkspaceDto>> getWorkspaces() {
        List<WorkspaceDto> res = new ArrayList<>();
        for (Workspace workspace : service.getAll()) {
            res.add(service.dtoFromEntity(workspace));
        }

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkspaceDto> getWorkspace(@PathVariable UUID id) {
        Workspace unpackedWorkspace = service.getById(id)
                                             .orElseThrow(() -> new EntityNotFoundException(id));

        return new ResponseEntity<>(service.dtoFromEntity(unpackedWorkspace),
                                    HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<WorkspaceDto> createWorkspace(@RequestBody
                                                        @JsonView(JsonDtoView.Creation.class)
                                                                WorkspaceDto dto) {

        Workspace workspace = service.create(dto);

        return new ResponseEntity<>(service.dtoFromEntity(workspace),
                                    HttpStatus.CREATED);
    }

    @PutMapping("/")
    public ResponseEntity<WorkspaceDto> updateWorkspace(@RequestBody WorkspaceDto dto) {
        Workspace persistedUpdate = service.update(dto);

        return new ResponseEntity<>(service.dtoFromEntity(persistedUpdate),
                                    HttpStatus.OK);
    }

    // TODO: implement a soft delete (archive) as well
    @DeleteMapping("/{id}")
    public HttpStatus deleteWorkspace(@PathVariable UUID id) {
        service.deleteById(id);

        return HttpStatus.NO_CONTENT;
    }
}
