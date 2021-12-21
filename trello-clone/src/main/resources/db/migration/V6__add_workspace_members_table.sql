create table workspace_members
(
    user_id      uuid references users (id)      not null,
    workspace_id uuid references workspaces (id) not null,

    role         user_role                       not null default 'GUEST',

    primary key (user_id, workspace_id)
)