create table cards
(
    id               uuid primary key,
    cardList_id      uuid        not null references cardlists (id),
    position         integer     not null,

    unique (cardList_id, position),

    page_title       varchar(50) not null default 'My Card',
    page_name        varchar(25) not null,
    page_description varchar(200),

    unique (cardList_id, page_name),

    active           bool        not null default true,

    reminder_id      uuid unique null references reminders (id)
/*per postgre's manual: UNIQUE NULLABLE constraint allows any number of rows to be null*/
)