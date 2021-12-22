create table boards
(
    id               uuid primary key,
    workspace_id     uuid references workspaces (id) not null,

    /* replaced by adding "OWNER" option to the Role enum
    created_by uuid references workspace_members(user_id, workspace_id) not null,
     */
    created_when     timestamptz                     not null,
    /* problematic, better be implemented via logging and aspects
    last_updated_by uuid references workspace_members(user_id, workspace_id),
    last_updated_when timestamptz,
    */

    page_title       varchar(20)                     not null default 'Marketing Dpmnt',
    page_name        varchar(20)                     not null,
    page_description varchar(500),

    unique (workspace_id, page_name),

    visibility       board_visibility                not null default 'WORKSPACE',
    active           bool                            not null default TRUE
)