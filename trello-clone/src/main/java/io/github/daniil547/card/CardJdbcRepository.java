package io.github.daniil547.card;

import io.github.daniil547.common.repositories.PageJdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

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
        return List.of("cardlist_id", "position", "active");
    }

    @Override
    protected void fillEntitySpecificQueryParams(Card card, PreparedStatement stmnt, Integer startingWith) throws SQLException {
        stmnt.setObject(startingWith, card.getCardListId());
        stmnt.setInt(startingWith + 1, card.getPosition());
        stmnt.setBoolean(startingWith + 2, card.getActive());

    }

    @Override
    protected Card fillEntitySpecificFields(ResultSet resultSet) throws SQLException {
        Card card = new Card();

        card.setCardListId(resultSet.getObject("cardlist_id", UUID.class));
        card.setPosition(resultSet.getInt("position"));
        card.setActive(resultSet.getBoolean("active"));

        return card;
    }
}