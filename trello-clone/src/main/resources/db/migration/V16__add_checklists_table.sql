create table checklists
(
    id       uuid primary key,
    name     varchar(60),

    card_id  uuid     not null references cards (id),
    position smallint not null,
    unique (card_id, position)
)