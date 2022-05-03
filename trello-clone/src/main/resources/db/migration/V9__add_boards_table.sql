create table boards
(
    id               uuid primary key,
    workspace_id     uuid             not null references workspaces (id),

    page_title       varchar(50)      not null default 'My board',
    page_name        varchar(20)      not null,
    page_description varchar(750),

    unique (workspace_id, page_name),

    visibility       board_visibility not null default 'WORKSPACE',
    active           bool             not null default TRUE
)