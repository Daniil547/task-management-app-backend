create type user_role as enum (
    'OWNER',
    'ADMIN',
    'MEMBER',
    'GUEST'
    )