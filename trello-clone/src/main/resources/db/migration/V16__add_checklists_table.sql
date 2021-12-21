create table checklists
(
    id       uuid primary key,
    name     varchar(60),

    card_id  uuid references cards (id) not null,
    position smallint                   not null,
    unique (card_id, position)
)