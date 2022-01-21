create table board_members
(
    member_id uuid primary key,
    user_id   uuid      not null references users (id),
    board_id  uuid      not null references boards (id),

    unique (user_id, board_id),

    role      user_role not null default 'GUEST'
)