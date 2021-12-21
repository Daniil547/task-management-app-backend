create table users
(
    id           uuid primary key,
    created_when timestamptz not null,

    username     varchar(20) not null unique,
    first_name   varchar(20) not null,
    last_name    varchar(20) not null,
    email        varchar(40) not null unique,
    about        varchar(250)
)