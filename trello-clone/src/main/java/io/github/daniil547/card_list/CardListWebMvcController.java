package io.github.daniil547.card_list;

import io.github.daniil547.common.controllers.PageWebMvcController;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}