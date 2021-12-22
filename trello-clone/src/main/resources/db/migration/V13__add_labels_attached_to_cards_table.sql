create table labels_attached_to_cards
(
    label_id uuid references labels (id) not null,
    card_id  uuid references cards (id)  not null,

    primary key (label_id, card_id)
)