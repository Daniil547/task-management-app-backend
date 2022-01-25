package io.github.daniil547.card_list;

import io.github.daniil547.common.repositories.PageJdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
class CardListJdbcRepository extends PageJdbcRepository<CardList> implements CardListRepository {

    @Autowired
    protected CardListJdbcRepository(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected String getTableName() {
        return "cardlists";
    }

    @Override
    protected List<String> getEntitySpecificColumns() {
        return List.of("board_id", "position", "active");
    }

}
