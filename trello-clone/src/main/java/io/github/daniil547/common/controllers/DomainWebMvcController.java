package io.github.daniil547.common.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import io.github.daniil547.common.domain.Domain;
import io.github.daniil547.common.dto.DomainDto;
import io.github.daniil547.common.exceptions.EntityNotFoundException;
import io.github.daniil547.common.services.DomainConverter;
import io.github.daniil547.common.services.DomainService;
import io.github.daniil547.common.util.JsonDtoView;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.tuple.Triple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Map;
import java.util.UUID;

public abstract class DomainWebMvcController<D extends DomainDto, E extends Domain> {

    protected Map<
            Triple<UUID, String, Pageable>, // key type
            ResponseEntity<Page<D>>         // value type
            > cacheForGetAll;

    //public abstract SearchQueryParser<E> searchQueryParser();
    public abstract DomainService<D, E> service();

    public abstract DomainConverter<D, E> converter();

    /**
     * That's a really uncool workaround.
     *
     * @param userId   id of the requester
     * @param search   search criteria
     * @param pageable pageable query
     * @return response entity to return to the user
     */
    protected abstract ResponseEntity<Page<D>> handleGetAllWithRespectToUser(UUID userId, String search, Pageable pageable);

    @GetMapping("/")
    @ApiOperation(value = "Returns all objects of this type that match given search criteria",
                  notes = "Supports pagination." +
                          " If no matching objects found, returns an empty OK response")
    public ResponseEntity<Page<D>> getAll(@RequestParam(value = "search",
                                                        required = false)
                                          String search,
                                          Pageable pageable,
                                          Authentication authentication) {

        UUID userId = (UUID) authentication.getPrincipal();

        return handleGetAllWithRespectToUser(userId, search, pageable);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Returns one object with the given ID",
                  notes = "If there's no object with the given ID returns 404.")
    public ResponseEntity<D> getById(@PathVariable
                                     @ApiParam(name = "id",
                                               value = "id (uuid) of an object you want to get",
                                               required = true)
                                     UUID id) {
        E unpackedEntity = service().repository().findById(id)
                                    .orElseThrow(() -> new EntityNotFoundException(id));

        return new ResponseEntity<>(converter().dtoFromEntity(unpackedEntity),
                                    HttpStatus.OK);
    }

    @PostMapping("/")
    @ApiOperation(value = "Creates one object from the given JSON",
                  notes = "The JSON must NOT contain \"id\" field," +
                          " it is to be set by the server. In case ID is" +
                          " sent no error will be thrown, so make sure" +
                          " to test client app for this")
    public ResponseEntity<D> create(@RequestBody
                                    @JsonView(JsonDtoView.Creation.class)
                                    @ApiParam(name = "object",
                                              value = "json representing a object, but with NO ID",
                                              required = true)
                                    @Valid D dto) {
        service().validate(dto);

        E createdEntity = service().repository().insert(converter().entityFromDto(dto));

        return new ResponseEntity<>(converter().dtoFromEntity(createdEntity),
                                    HttpStatus.CREATED);
    }

    @PutMapping("/")
    @ApiOperation(value = "Updates one object with the values given JSON",
                  notes = "The JSON must CONTAIN \"id\" field" +
                          " for server to know which entity to update.")
    public ResponseEntity<D> update(@RequestBody
                                    @ApiParam(name = "object",
                                              value = "json representing a object, WITH an ID",
                                              required = true)
                                    @Valid D dto) {
        service().validate(dto);

        E persistedUpdate = service().repository().update(converter().entityFromDto(dto));

        return new ResponseEntity<>(converter().dtoFromEntity(persistedUpdate),
                                    HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Performs a hard delete of one object with the given ID",
                  notes = "The JSON must CONTAIN \"id\" field" +
                          " for server to know which entity to update.")
    public ResponseEntity<?> deleteById(@PathVariable
                                        @ApiParam(name = "id",
                                                  value = "id (uuid) of an object you want to delete",
                                                  required = true)
                                        UUID id) {
        service().repository().deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
