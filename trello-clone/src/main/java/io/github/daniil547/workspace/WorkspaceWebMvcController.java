package io.github.daniil547.workspace;

import io.github.daniil547.board.BoardVisibility;
import io.github.daniil547.common.controllers.PageWebMvcController;
import io.github.daniil547.common.rest_api_search.SearchCriterion;
import io.github.daniil547.common.rest_api_search.SearchOperation;
import io.github.daniil547.common.rest_api_search.SearchSpecification;
import io.github.daniil547.user.member.WorkspaceMemberRepository;
import io.swagger.annotations.Api;
import org.apache.commons.collections4.map.LRUMap;
import org.apache.commons.lang3.tuple.Triple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/workspaces")
@Api(tags = "Workspace Resource", description = "CRUD endpoint for workspaces")
public class WorkspaceWebMvcController extends PageWebMvcController<WorkspaceDto, Workspace> {
    private final WorkspaceService service;
    private final WorkspaceConverter converter;

    private final WorkspaceMemberRepository workspaceMemberRepo;

    @Autowired
    public WorkspaceWebMvcController(WorkspaceService service,
                                     WorkspaceConverter converter,
                                     WorkspaceMemberRepository workspaceMemberRepo,
                                     @Value("${mvc.workspaces.get-all-cache-size}")
                                     int cacheSize) {
        this.service = service;
        this.converter = converter;
        this.workspaceMemberRepo = workspaceMemberRepo;

        cacheForGetAll = Collections.synchronizedMap(new LRUMap<>(cacheSize));
    }

    @Override
    public WorkspaceService service() {
        return this.service;
    }

    @Override
    public WorkspaceConverter converter() {
        return this.converter;
    }

    /**
     * Finds only boards that are directly available to the user
     * (he is a member in them)
     *
     * @param userId   id of the requester
     * @param search   search criteria
     * @param pageable pageable query
     * @return
     */
    @Override
    protected ResponseEntity<Page<WorkspaceDto>> handleGetAllWithRespectToUser(UUID userId, String search, Pageable pageable) {
        Triple<UUID, String, Pageable> args = Triple.of(userId, search, pageable);
        if (cacheForGetAll.containsKey(args)) {
            return cacheForGetAll.get(args);
        }
        Specification<Workspace> spec = service().searchQueryParser().parse(search);
        if (userId == null) {
            spec = spec.and(new SearchSpecification<>(
                    new SearchCriterion("visibility",
                                        SearchOperation.EQUALITY,
                                        BoardVisibility.PUBLIC))
            );
        } else {
            List<UUID> associatedWorkspaces = workspaceMemberRepo.findWorkspaceIdsByUserId(userId);

            Specification<Workspace> tmpSpec = new SearchSpecification<>(
                    new SearchCriterion("id",
                                        SearchOperation.EQUALITY,
                                        associatedWorkspaces.get(0))
            );

            for (UUID uuid : associatedWorkspaces.subList(1, associatedWorkspaces.size())) {
                tmpSpec = tmpSpec.or(new SearchSpecification<>(
                        new SearchCriterion("visibility",
                                            SearchOperation.EQUALITY,
                                            WorkspaceVisibility.PUBLIC))
                );
            }

            spec.and(tmpSpec);
        }
        Page<WorkspaceDto> res = service().repository()
                                          .findAll(spec, pageable)
                                          .map(converter()::dtoFromEntity);

        ResponseEntity<Page<WorkspaceDto>> response = new ResponseEntity<>(res, HttpStatus.OK);
        cacheForGetAll.put(args, response);

        return response;
    }
}
