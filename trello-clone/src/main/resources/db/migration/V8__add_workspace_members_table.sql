create table workspace_members
(
    member_id    uuid primary key,

    user_id      uuid      not null references users (id),
    workspace_id uuid      not null references workspaces (id),

    role         user_role not null default 'GUEST'
);
