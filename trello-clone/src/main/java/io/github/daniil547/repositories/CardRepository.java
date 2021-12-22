package io.github.daniil547.repositories;

import io.github.daniil547.DataSourceManager;
import io.github.daniil547.domain.Card;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

public class CardRepository extends AbstractRepository<Card> {


    public boolean saveCard(Card card) {
        String template = """
                            INSERT INTO trello_clone_db.public.cards (
                                  id, cardlist_id, position, created_when, page_name, page_title, page_description
                              ) VALUES (
                                  '%1$s', null, %2$d, '%3$tY-%3$tm-%3$td %3$tH:%3$tM:%3$tS%3$tz', '%4$s', '%5$s', '%6$s'
                              );           /*^*/
                          """;     /*TODO:   ^  how to fill position correctly?  */
        String query = String.format(template,
                                     card.getId().toString(),                           //1
                                     lastCardPosition() + 1,                            //2
                                     GregorianCalendar.from(card.getCreatedWhen()), //3
                                     card.getName(),                                    //4
                                     card.getTitle(),                                   //5
                                     card.getDescription()                              //6
                                    );
        System.out.println(query);
        return execute(query);
    }

    public int lastCardPosition() {
        String query = """
                       SELECT max(position)
                       FROM trello_clone_db.public.cards
                       """;
        return retrieveInt(query);
    }

    /**
     * Acts as both update and (soft) delete (if the Card with active = false is passed)
     *
     * @param card Card DTO object
     * @return whether query succeeds
     */
    public boolean updateCard(Card card) {
        String template = """
                          UPDATE trello_clone_db.public.cards
                          set page_title = '%s', page_description = '%s', active = %b
                          where id = '%s'
                          """;
        String query = String.format(template,
                                     card.getTitle(),
                                     card.getDescription(),
                                     card.getActive(),
                                     card.getId().toString());

        return execute(query);
    }

    //TODO add getBy*(...) methods
    public List<Card> getAllCards() {
        String query = """
                       SELECT * FROM trello_clone_db.public.cards
                       ORDER BY position asc
                       """;

        try (Connection connection = DataSourceManager.getConnection()) {
            try (PreparedStatement stmnt = connection
                    .prepareStatement(query)) {
                ResultSet resultSet = stmnt.executeQuery();
                List<Card> cards = new ArrayList<>(); //TODO query count of cards from DB
                Card tmpCard;
                while (resultSet.next()) {
                    tmpCard = new Card();
                    tmpCard.setId(UUID.fromString(resultSet.getString("id")));
                    tmpCard.setTitle(resultSet.getString("page_title"));
                    tmpCard.setDescription(resultSet.getString("page_description"));
                    tmpCard.setCreatedWhen(resultSet.getTimestamp("created_when").toInstant().atZone(ZoneId.of("+02")));
                    cards.add(tmpCard);
                }
                return cards;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


}
