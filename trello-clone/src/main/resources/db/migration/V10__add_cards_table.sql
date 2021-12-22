create table cards
(
    id               uuid primary key,
    cardlist_id      uuid references cardlists (id) not null,
    position         smallint                       not null,

    unique (cardlist_id, position),

    /* replaced by adding is creator
    created_by uuid references workspace_members(user_id, workspace_id) not null,
     */
    created_when     timestamptz                    not null,
    /* problematic, better be implemented via logging and aspects
    last_updated_by uuid references workspace_members(user_id, workspace_id),
    last_updated_when timestamptz,
    */

    page_title       varchar(20)                    not null default 'My Card',
    page_name        varchar(25)                    not null,
    page_description varchar(200),

    unique (cardlist_id, page_name),

    active           bool                           not null default true
)