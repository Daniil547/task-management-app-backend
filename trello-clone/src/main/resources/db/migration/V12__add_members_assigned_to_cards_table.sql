create table members_assigned_to_cards
(
    member_id uuid references board_members (member_id),
    card_id   uuid references cards (id),

    /*creator bool default FALSE, doable but messy*/
    assigned  bool not null default FALSE,

    primary key (member_id, card_id)
)