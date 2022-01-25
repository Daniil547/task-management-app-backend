package io.github.daniil547.card;

import io.github.daniil547.common.repositories.PageJdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
class CardJdbcRepository extends PageJdbcRepository<Card> implements CardRepository {

    @Autowired
    public CardJdbcRepository(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected String getTableName() {
        return "cards";
    }

    @Override
    protected List<String> getEntitySpecificColumns() {
        return List.of("cardList_id", "position", "active");
    }

}