create table cards
(
    id               uuid primary key,
    cardlist_id      uuid references cardlists (id) not null,
    position         smallint                       not null,

    unique (cardlist_id, position),

    page_title       varchar(20)                    not null default 'My Card',
    page_name        varchar(25)                    not null,
    page_description varchar(200),

    unique (cardlist_id, page_name),

    active           bool                           not null default true
)