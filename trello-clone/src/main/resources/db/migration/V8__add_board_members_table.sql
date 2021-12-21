create table board_members
(
    member_id uuid primary key,
    user_id   uuid references users (id)  not null,
    board_id  uuid references boards (id) not null,

    unique (user_id, board_id),

    role      user_role                   not null default 'GUEST'
)