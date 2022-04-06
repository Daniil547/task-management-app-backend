create table checklists
(
    id       uuid primary key,
    name     varchar(60),

    card_id  uuid not null references cards (id),
    position int4 not null,
    unique (card_id, position)
)