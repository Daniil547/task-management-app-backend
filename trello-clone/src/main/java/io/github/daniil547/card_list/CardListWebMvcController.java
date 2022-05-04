package io.github.daniil547.card_list;

import io.github.daniil547.common.controllers.PageWebMvcController;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.tuple.Triple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/card-lists")
@Api(tags = "Card list Resource", description = "CRUD endpoint for card lists")
public class CardListWebMvcController extends PageWebMvcController<CardListDto, CardList> {
    private final CardListService service;
    private final CardListConverter converter;

    @Autowired
    public CardListWebMvcController(CardListService service, CardListConverter converter) {
        this.service = service;
        this.converter = converter;
    }

    @Override
    public CardListService service() {
        return this.service;
    }

    @Override
    public CardListConverter converter() {
        return this.converter;
    }

    @Override
    protected ResponseEntity<Page<CardListDto>> handleGetAllWithRespectToUser(UUID userId, String search, Pageable pageable) {
        Triple<UUID, String, Pageable> args = Triple.of(userId, search, pageable);
        if (cacheForGetAll.containsKey(args)) {
            return cacheForGetAll.get(args);
        }
        Specification<CardList> spec = service().searchQueryParser().parse(search);
        if (userId == null) {
            // I actually don't know what to do in this case
        } else {

        }
        Page<CardListDto> res = service().repository()
                                         .findAll(spec, pageable)
                                         .map(converter()::dtoFromEntity);

        ResponseEntity<Page<CardListDto>> response = new ResponseEntity<>(res, HttpStatus.OK);
        cacheForGetAll.put(args, response);

        // FIXME
        throw new NotImplementedException();
    }
}