create table cards
(
    id               uuid primary key,
    cardList_id      uuid        not null references cardlists (id),
    position         smallint    not null,

    unique (cardList_id, position),

    page_title       varchar(20) not null default 'My Card',
    page_name        varchar(25) not null,
    page_description varchar(200),

    unique (cardList_id, page_name),

    active           bool        not null default true
)