create table users
(
    id            uuid primary key,

    profile_title varchar(50),

    username      varchar(20)  not null unique,
    first_name    varchar(20)  not null,
    last_name     varchar(20)  not null,
    email         varchar(254) not null unique,
    about         varchar(350)
)