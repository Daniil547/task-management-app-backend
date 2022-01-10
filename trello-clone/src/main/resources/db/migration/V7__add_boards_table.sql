create table boards
(
    id               uuid primary key,
    workspace_id     uuid references workspaces (id) not null,

    page_title       varchar(20)                     not null default 'Marketing Dpmnt',
    page_name        varchar(20)                     not null,
    page_description varchar(500),

    unique (workspace_id, page_name),

    visibility       board_visibility                not null default 'WORKSPACE',
    active           bool                            not null default TRUE
)