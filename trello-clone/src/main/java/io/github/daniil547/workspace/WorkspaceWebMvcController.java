package io.github.daniil547.workspace;

import com.fasterxml.jackson.annotation.JsonView;
import io.github.daniil547.common.exceptions.EntityNotFoundException;
import io.github.daniil547.common.util.JsonDtoView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@Api(tags = "Workspace Resource", description = "CRUD endpoint for worksapces")
public class WorkspaceWebMvcController {
    private final WorkspaceService service;

    @Autowired
    public WorkspaceWebMvcController(WorkspaceService service) {
        this.service = service;
    }

    //TODO add pagination after migration to Data JPA
    @GetMapping("/")
    @ApiOperation(value = "Returns all the workspaces",
                  notes = "Doesn't support pagination (yet)." +
                          " If there are no workspaces, returns nothing")
    public ResponseEntity<List<WorkspaceDto>> getWorkspaces() {
        List<WorkspaceDto> res = new ArrayList<>();
        for (Workspace workspace : service.getAll()) {
            res.add(service.dtoFromEntity(workspace));
        }

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Returns one workspace with the given ID",
                  notes = "If there's no workspace with the given ID returns 404.")
    public ResponseEntity<WorkspaceDto> getWorkspace(@PathVariable
                                                     @ApiParam(name = "id",
                                                               value = "id (uuid) of a workspace you want get")
                                                             UUID id) {
        Workspace unpackedWorkspace = service.getById(id)
                                             .orElseThrow(() -> new EntityNotFoundException(id));

        return new ResponseEntity<>(service.dtoFromEntity(unpackedWorkspace),
                                    HttpStatus.OK);
    }

    @PostMapping("/")
    @ApiOperation(value = "Creates one workspace from the given JSON",
                  notes = "The JSON must NOT contain \"id\" field," +
                          " it is to be set by the server. In case ID is" +
                          " sent no error will be thrown, so make sure" +
                          " to test client app for this")
    public ResponseEntity<WorkspaceDto> createWorkspace(@RequestBody
                                                        @JsonView(JsonDtoView.Creation.class)
                                                        @ApiParam(name = "workspace",
                                                                  value = "json representing a workspace, but with NO ID")
                                                                WorkspaceDto dto) {

        Workspace workspace = service.create(dto);

        return new ResponseEntity<>(service.dtoFromEntity(workspace),
                                    HttpStatus.CREATED);
    }

    @PutMapping("/")
    @ApiOperation(value = "Updates one workspace with the values given JSON",
                  notes = "The JSON must CONTAIN \"id\" field" +
                          " for server to know which entity to update.")
    public ResponseEntity<WorkspaceDto> updateWorkspace(@RequestBody
                                                        @ApiParam(name = "workspace",
                                                                  value = "json representing a workspace, WITH an ID")
                                                                WorkspaceDto dto) {
        Workspace persistedUpdate = service.update(dto);

        return new ResponseEntity<>(service.dtoFromEntity(persistedUpdate),
                                    HttpStatus.OK);
    }

    // TODO: implement a soft delete (archive) as well
    @DeleteMapping("/{id}")
    @ApiOperation(value = "Performs a hard delete of one workspace with the given ID",
                  notes = "The JSON must CONTAIN \"id\" field" +
                          " for server to know which entity to update.")
    public HttpStatus deleteWorkspace(@PathVariable
                                      @ApiParam(name = "id",
                                                value = "id (uuid) of a workspace you want to delete")
                                              UUID id) {
        service.deleteById(id);

        return HttpStatus.NO_CONTENT;
    }
}
