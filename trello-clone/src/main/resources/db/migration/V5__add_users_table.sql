create table users
(
    id           uuid primary key,
    created_when timestamptz not null,

    page_title   varchar(50) not null default 'User Profile',

    username     varchar(20) not null unique,
    first_name   varchar(20) not null,
    last_name    varchar(20) not null,
    email        varchar(40) not null unique,
    about        varchar(250)
)