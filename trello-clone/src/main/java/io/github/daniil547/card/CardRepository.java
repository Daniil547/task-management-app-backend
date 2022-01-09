package io.github.daniil547.card;

import io.github.daniil547.common.repositories.PageSqlRepository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class CardRepository extends PageSqlRepository<Card> {


    public CardRepository(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected String getTableName() {
        return "card";
    }

    @Override
    protected List<String> getColumns() {
        return Arrays.asList("id", "page_title", "page_name", "page_description", "cardlist_id", "position");
    }

    @Override
    protected void fillEntitySpecificQueryParams(Card card, PreparedStatement stmnt, Integer startingWith) throws SQLException {
        stmnt.setObject(startingWith + 3, card.getCardListId());
        stmnt.setInt(startingWith + 4, card.getPosition());
    }

    @Override
    protected Card fillEntitySpecificFields(ResultSet resultSet) throws SQLException {
        Card tmpCard;
        tmpCard = new Card();
        tmpCard.setCardListId(resultSet.getObject("cardlist_id", UUID.class));
        tmpCard.setPosition(resultSet.getInt("position"));
        return tmpCard;
    }
}