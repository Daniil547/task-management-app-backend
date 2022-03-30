create table labels_attached_to_cards
(
    label_id uuid not null references labels (id),
    card_id  uuid not null references cards (id),

    primary key (label_id, card_id)
)