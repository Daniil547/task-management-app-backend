create table labels
(
    id       uuid primary key,
    name     varchar(100),
    color    int4 not null default 0,

    board_id uuid not null references boards (id)
)