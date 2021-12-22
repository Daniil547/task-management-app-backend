create table labels
(
    id       uuid primary key,
    name     varchar(40),
    color    int4                        not null default 0,

    board_id uuid references boards (id) not null
)