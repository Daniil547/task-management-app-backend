create table users_profiles
(
    user_id           uuid primary key references users (id),

    profile_title     varchar(50) not null default 'My board',
    profile_page_name varchar(30) not null,
    about             varchar(350),

    first_name        varchar(20) not null,
    last_name         varchar(20) not null
)