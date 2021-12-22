create table cardlists
(
    id               uuid primary key,
    board_id         uuid references boards (id)               not null,
    position         smallint                                  not null,

    unique (board_id, position),

    created_by       uuid references board_members (member_id) not null,
    created_when     timestamptz                               not null,
    /* problematic, better be implemented via logging and aspects
    last_updated_by uuid references workspace_members(user_id, workspace_id),
    last_updated_when timestamptz,
    */

    page_title       varchar(20)                               not null default 'My List',
    page_name        varchar(25)                               not null,
    page_description varchar(300),

    unique (board_id, page_name),

    active           bool                                      not null default TRUE
)